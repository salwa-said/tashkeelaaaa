package com.omanfootball.manager.service;


import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;


import com.omanfootball.manager.entity.SharedFantasyTeam;
import com.omanfootball.manager.repository.SharedFantasyTeamRepository;
import com.omanfootball.manager.repository.FantasyTeamRepository;
import com.omanfootball.manager.repository.FantasyTeamPlayerRepository;
import com.omanfootball.manager.repository.UserRepository;
import com.omanfootball.manager.entity.FantasyTeam;
import com.omanfootball.manager.entity.FantasyTeamPlayer;
import com.omanfootball.manager.entity.Player;
import com.omanfootball.manager.entity.User;
import com.omanfootball.manager.dto.SharedTeamView;
import com.omanfootball.manager.dto.TeamMemberResponse;
import java.util.UUID;
import java.util.Comparator;



@Service
@RequiredArgsConstructor
public class ShareTeamService {



    private final SharedFantasyTeamRepository repository;

    private final FantasyTeamRepository fantasyTeamRepository;

    private final FantasyTeamPlayerRepository teamPlayerRepository;

    private final UserRepository userRepository;

    @Transactional
    public SharedFantasyTeam shareMine(String email, String shareUrl) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        FantasyTeam team = fantasyTeamRepository.findByUser_Id(user.getId())
                .orElseThrow(() -> new IllegalArgumentException("Create your squad before sharing it"));
        SharedFantasyTeam shared = repository.findByUser_Id(user.getId()).orElseGet(SharedFantasyTeam::new);
        if (shared.getShareCode() == null) {
            shared.setShareCode(UUID.randomUUID().toString().replace("-", "").substring(0, 12));
        }
        shared.setUser(user);
        shared.setFantasyTeam(team);
        shared.setShareUrl(shareUrl.replace("PLACEHOLDER", shared.getShareCode()));
        return repository.save(shared);
    }

    @Transactional(readOnly = true)
    public SharedTeamView getPublicTeam(String shareCode) {
        SharedFantasyTeam shared = repository.findByShareCode(shareCode)
                .orElseThrow(() -> new IllegalArgumentException("Shared squad not found"));
        FantasyTeam team = shared.getFantasyTeam();
        List<TeamMemberResponse> players = teamPlayerRepository.findByFantasyTeam_TeamId(team.getTeamId()).stream()
                .sorted(Comparator.comparing(FantasyTeamPlayer::getId))
                .map(entry -> {
                    Player player = entry.getPlayer();
                    return new TeamMemberResponse(entry.getId(), player.getId(), player.getName(),
                            entry.getPosition(), player.getClub() == null ? "Unassigned" : player.getClub().getClubName(),
                            entry.getPrice(), entry.getOverallRating() == null ? 0.0 : entry.getOverallRating().doubleValue(),
                            player.getImage(), entry.getFormationSlot());
                }).toList();
        return new SharedTeamView(team.getTeamName(), team.getFormation(), team.getTotalPoints(),
                team.getOverallRating(), players);
    }





    public List<SharedFantasyTeam> getAll() {

        return repository.findAll();

    }





    public SharedFantasyTeam getById(Long id) {


        return repository.findById(id)
                .orElseThrow(
                        () -> new RuntimeException(
                                "Shared team not found with id: " + id
                        )
                );

    }





    @Transactional
    public SharedFantasyTeam create(
            SharedFantasyTeam team
    ) {

        return repository.save(team);

    }





    @Transactional
    public SharedFantasyTeam update(
            Long id,
            SharedFantasyTeam newTeam
    ) {


        SharedFantasyTeam team = getById(id);



        team.setShareCode(
                newTeam.getShareCode()
        );



        team.setShareUrl(
                newTeam.getShareUrl()
        );



        team.setUser(
                newTeam.getUser()
        );



        team.setFantasyTeam(
                newTeam.getFantasyTeam()
        );



        return repository.save(team);

    }





    @Transactional
    public void delete(Long id) {


        SharedFantasyTeam team = getById(id);


        repository.delete(team);


    }


}
