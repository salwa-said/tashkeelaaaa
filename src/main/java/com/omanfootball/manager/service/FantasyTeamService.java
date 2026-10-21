package com.omanfootball.manager.service;


import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Value;


import java.util.List;
import java.util.ArrayList;
import java.util.Comparator;


import com.omanfootball.manager.entity.FantasyTeam;
import com.omanfootball.manager.repository.FantasyTeamRepository;
import com.omanfootball.manager.repository.FantasyTeamPlayerRepository;
import com.omanfootball.manager.repository.PlayerRepository;
import com.omanfootball.manager.repository.UserRepository;
import com.omanfootball.manager.dto.MyTeamResponse;
import com.omanfootball.manager.dto.TeamMemberResponse;
import com.omanfootball.manager.entity.FantasyTeamPlayer;
import com.omanfootball.manager.entity.Player;
import com.omanfootball.manager.entity.User;



@Service
@RequiredArgsConstructor
public class FantasyTeamService {

    @Value("${app.fantasy.starting-budget:1000}")
    private double startingBudget;


    private final FantasyTeamRepository repository;

    private final FantasyTeamPlayerRepository teamPlayerRepository;

    private final PlayerRepository playerRepository;

    private final UserRepository userRepository;

    private final FantasyScoringService fantasyScoringService;

    @Transactional
    public MyTeamResponse getMine(String email) {
        User user = getUser(email);
        FantasyTeam team = repository.findByUser_Id(user.getId()).orElse(null);
        if (team == null) {
            return new MyTeamResponse(null, "My Squad", startingBudget, 0, 0.0, "4-4-2", List.of());
        }
        String formation = normalizeFormation(team.getFormation());
        fantasyScoringService.refreshTeamPoints(team.getTeamId());
        if (!formation.equals(team.getFormation())) team.setFormation(formation);
        List<FantasyTeamPlayer> entries = teamPlayerRepository.findByFantasyTeam_TeamId(team.getTeamId());
        boolean updatedSlots = false;
        for (FantasyTeamPlayer entry : entries) {
            if (entry.getFormationSlot() == null) {
                try {
                    entry.setFormationSlot(firstOpenSlot(formation, entry.getPosition(), entries));
                    updatedSlots = true;
                } catch (IllegalArgumentException ignored) {
                    // Keep players who do not fit the active formation on the roster, off the pitch.
                }
            }
        }
        if (updatedSlots) teamPlayerRepository.saveAll(entries);
        List<TeamMemberResponse> players = entries.stream()
                .sorted(Comparator.comparing(FantasyTeamPlayer::getId))
                .map(entry -> {
                    Player player = entry.getPlayer();
                    return new TeamMemberResponse(entry.getId(), player.getId(), player.getName(),
                            entry.getPosition(), player.getClub() == null ? "Unassigned" : player.getClub().getClubName(),
                            entry.getPrice(), entry.getOverallRating() == null ? 0.0 : entry.getOverallRating().doubleValue(),
                            player.getImage(), entry.getFormationSlot(), player.getFantasyPoints(), entry.getIsCaptain());
                }).toList();
        return new MyTeamResponse(team.getTeamId(), team.getTeamName(), team.getBudget(),
                team.getTotalPoints(), team.getOverallRating(), normalizeFormation(team.getFormation()), players);
    }

    @Transactional
    public MyTeamResponse createMine(String email, String teamName, String formation) {
        User user = getUser(email);
        if (repository.findByUser_Id(user.getId()).isEmpty()) {
            if (teamName == null || teamName.isBlank()) {
                throw new IllegalArgumentException("Enter a name for your squad");
            }
            FantasyTeam team = new FantasyTeam();
            team.setTeamName(teamName.trim());
            team.setFormation(normalizeFormation(formation));
            team.setBudget(startingBudget);
            team.setTotalPoints(0);
            team.setOverallRating(0.0);
            team.setUser(user);
            repository.save(team);
        }
        return getMine(email);
    }

    @Transactional
    public MyTeamResponse addPlayerToMine(String email, Long playerId, String slot) {
        User user = getUser(email);
        FantasyTeam team = repository.findByUser_Id(user.getId())
                .orElseThrow(() -> new IllegalArgumentException("Create your squad before adding players"));
        Player player = playerRepository.findById(playerId)
                .orElseThrow(() -> new IllegalArgumentException("Player not found"));
        List<FantasyTeamPlayer> entries = teamPlayerRepository.findByFantasyTeam_TeamId(team.getTeamId());
        if (teamPlayerRepository.existsByFantasyTeam_TeamIdAndPlayer_Id(team.getTeamId(), playerId)) {
            throw new IllegalArgumentException("This player is already in your squad");
        }
        String normalizedSlot = slot == null || slot.isBlank()
                ? firstOpenSlot(team.getFormation(), player.getPosition(), entries)
                : validateSlot(team.getFormation(), slot, player.getPosition());
        FantasyTeamPlayer occupant = entries.stream()
                .filter(entry -> normalizedSlot.equals(entry.getFormationSlot()))
                .findFirst().orElse(null);
        if (occupant == null && entries.size() >= 11) {
            throw new IllegalArgumentException("A squad can contain up to 11 players");
        }
        double price = player.getPrice();
        double budget = team.getBudget() == null ? startingBudget : team.getBudget();
        double availableBudget = budget + (occupant == null || occupant.getPrice() == null ? 0.0 : occupant.getPrice());
        if (price > availableBudget) {
            throw new IllegalArgumentException("Not enough budget for this player");
        }
        if (occupant == null) {
            occupant = new FantasyTeamPlayer();
            occupant.setFantasyTeam(team);
            occupant.setIsCaptain(false);
            entries.add(occupant);
        }
        occupant.setPlayer(player);
        occupant.setPosition(player.getPosition());
        occupant.setFormationSlot(normalizedSlot);
        occupant.setPrice(price);
        occupant.setOverallRating((int) Math.round(player.getOverallRating()));
        teamPlayerRepository.save(occupant);
        team.setBudget(availableBudget - price);
        team.setOverallRating(entries.stream().mapToDouble(entry -> entry.getOverallRating() == null
                ? 0.0 : entry.getOverallRating().doubleValue()).average().orElse(0.0));
        team.setTotalPoints(entries.stream().mapToInt(this::memberPoints).sum());
        repository.save(team);
        return getMine(email);
    }

    @Transactional
    public MyTeamResponse changeFormation(String email, String formation) {
        User user = getUser(email);
        FantasyTeam team = repository.findByUser_Id(user.getId())
                .orElseThrow(() -> new IllegalArgumentException("Create your squad before choosing a formation"));
        String nextFormation = normalizeFormation(formation);
        if (!nextFormation.equals(normalizeFormation(team.getFormation()))) {
            List<FantasyTeamPlayer> entries = teamPlayerRepository.findByFantasyTeam_TeamId(team.getTeamId());
            entries.forEach(entry -> entry.setFormationSlot(null));
            for (FantasyTeamPlayer entry : entries) {
                try {
                    entry.setFormationSlot(firstOpenSlot(nextFormation, entry.getPosition(), entries));
                } catch (IllegalArgumentException ignored) {
                    // Players without a position in the new formation remain in the squad but off the pitch.
                }
            }
            teamPlayerRepository.saveAll(entries);
            team.setFormation(nextFormation);
            repository.save(team);
        }
        return getMine(email);
    }

    private String normalizeFormation(String formation) {
        if (formation == null || formation.isBlank()) return "4-4-2";
        String value = formation.trim();
        if (!List.of("4-4-2", "4-3-3", "3-5-2", "4-2-3-1").contains(value)) {
            throw new IllegalArgumentException("Choose a supported formation");
        }
        return value;
    }

    private String validateSlot(String formation, String slot, String playerPosition) {
        if (slot == null || !slot.matches("(?:GK|DEF[1-5]|MID[1-5]|FWD[1-3])")) {
            throw new IllegalArgumentException("Choose a valid position on the pitch");
        }
        String selectedFormation = normalizeFormation(formation);
        int defenders = selectedFormation.equals("3-5-2") ? 3 : 4;
        int midfielders = selectedFormation.equals("4-4-2") ? 4 : selectedFormation.equals("4-3-3") ? 3 : 5;
        int forwards = selectedFormation.equals("4-4-2") || selectedFormation.equals("3-5-2") ? 2 : selectedFormation.equals("4-3-3") ? 3 : 1;
        int slotNumber = slot.equals("GK") ? 1 : Integer.parseInt(slot.replaceAll("\\D", ""));
        int capacity = slot.startsWith("DEF") ? defenders : slot.startsWith("MID") ? midfielders : slot.startsWith("FWD") ? forwards : 1;
        if (slotNumber > capacity) throw new IllegalArgumentException("This position is not part of the selected formation");
        String requiredPosition = slot.startsWith("GK") ? "goalkeeper" : slot.startsWith("DEF") ? "defender"
                                                                         : slot.startsWith("MID") ? "midfielder" : "forward";
        String actualPosition = playerPosition == null ? "" : playerPosition.toLowerCase();
        if (!actualPosition.equals(requiredPosition)) {
            throw new IllegalArgumentException("Select a " + requiredPosition + " for this position");
        }
        return slot;
    }

    private String firstOpenSlot(String formation, String playerPosition, List<FantasyTeamPlayer> entries) {
        String actualPosition = playerPosition == null ? "" : playerPosition.toLowerCase();
        String prefix = switch (actualPosition) {
            case "goalkeeper" -> "GK";
            case "defender" -> "DEF";
            case "midfielder" -> "MID";
            case "forward" -> "FWD";
            default -> throw new IllegalArgumentException("This player's position does not fit a squad slot");
        };
        int capacity = prefix.equals("GK") ? 1 : prefix.equals("DEF")
                                                 ? (normalizeFormation(formation).equals("3-5-2") ? 3 : 4)
                                                 : prefix.equals("MID") ? (normalizeFormation(formation).equals("4-4-2") ? 4
                                                                           : normalizeFormation(formation).equals("4-3-3") ? 3 : 5)
                                                   : (normalizeFormation(formation).equals("4-4-2") || normalizeFormation(formation).equals("3-5-2") ? 2
                                                      : normalizeFormation(formation).equals("4-3-3") ? 3 : 1);
        for (int index = 1; index <= capacity; index++) {
            String candidate = prefix.equals("GK") ? "GK" : prefix + index;
            if (entries.stream().noneMatch(entry -> candidate.equals(entry.getFormationSlot()))) return candidate;
        }
        throw new IllegalArgumentException("No open position for this player in the selected formation");
    }

    @Transactional
    public MyTeamResponse removePlayerFromMine(String email, Long entryId) {
        User user = getUser(email);
        FantasyTeamPlayer entry = teamPlayerRepository.findById(entryId)
                .orElseThrow(() -> new IllegalArgumentException("Squad player not found"));
        FantasyTeam team = entry.getFantasyTeam();
        if (team == null || team.getUser() == null || !team.getUser().getId().equals(user.getId())) {
            throw new IllegalArgumentException("That player is not in your squad");
        }
        List<FantasyTeamPlayer> entries = teamPlayerRepository.findByFantasyTeam_TeamId(team.getTeamId());
        int remaining = Math.max(0, entries.size() - 1);
        team.setBudget((team.getBudget() == null ? 0.0 : team.getBudget()) + entry.getPrice());
        if (remaining == 0) {
            team.setOverallRating(0.0);
        } else {
            team.setOverallRating((team.getOverallRating() * entries.size() - entry.getOverallRating()) / remaining);
        }
        teamPlayerRepository.delete(entry);
        entries.remove(entry);
        team.setTotalPoints(entries.stream().mapToInt(this::memberPoints).sum());
        repository.save(team);
        return getMine(email);
    }

    @Transactional
    public MyTeamResponse setCaptain(String email, Long entryId) {
        User user = getUser(email);
        FantasyTeamPlayer selected = teamPlayerRepository.findById(entryId)
                .orElseThrow(() -> new IllegalArgumentException("Squad player not found"));
        FantasyTeam team = selected.getFantasyTeam();
        if (team == null || team.getUser() == null || !team.getUser().getId().equals(user.getId())) {
            throw new IllegalArgumentException("That player is not in your squad");
        }
        List<FantasyTeamPlayer> entries = teamPlayerRepository.findByFantasyTeam_TeamId(team.getTeamId());
        entries.forEach(entry -> entry.setIsCaptain(entry.getId().equals(entryId)));
        teamPlayerRepository.saveAll(entries);
        team.setTotalPoints(entries.stream().mapToInt(this::memberPoints).sum());
        repository.save(team);
        return getMine(email);
    }

    private int memberPoints(FantasyTeamPlayer entry) {
        int points = entry.getPlayer() == null ? 0 : entry.getPlayer().getFantasyPoints();
        return Boolean.TRUE.equals(entry.getIsCaptain()) ? points * 2 : points;
    }

    private User getUser(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
    }





    public List<FantasyTeam> getAllTeams() {

        return repository.findAll();

    }





    public FantasyTeam getTeamById(Long id) {


        return repository.findById(id)
                .orElseThrow(
                        () -> new RuntimeException(
                                "Fantasy team not found with id: " + id
                        )
                );

    }





    @Transactional
    public FantasyTeam createTeam(FantasyTeam team) {
        team.setTotalPoints(0);
        return repository.save(team);

    }





    @Transactional
    public FantasyTeam updateTeam(
            Long id,
            FantasyTeam newTeam
    ) {


        FantasyTeam team = getTeamById(id);



        team.setTeamName(
                newTeam.getTeamName()
        );



        team.setBudget(
                newTeam.getBudget()
        );



        team.setTotalPoints(teamPlayerRepository.findByFantasyTeam_TeamId(team.getTeamId())
                .stream().mapToInt(this::memberPoints).sum());



        team.setOverallRating(
                newTeam.getOverallRating()
        );



        team.setUser(
                newTeam.getUser()
        );



        return repository.save(team);

    }





    @Transactional
    public void deleteTeam(Long id) {


        FantasyTeam team = getTeamById(id);



        repository.delete(team);


    }


}
