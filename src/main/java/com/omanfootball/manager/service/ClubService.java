package com.omanfootball.manager.service;


import com.omanfootball.manager.entity.Club;
import com.omanfootball.manager.exception.ResourceNotFoundException;
import com.omanfootball.manager.repository.ClubRepository;


import lombok.RequiredArgsConstructor;


import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;



@Service
@RequiredArgsConstructor
public class ClubService {



    private final ClubRepository clubRepository;





    public List<Club> getAllClubs() {

        return clubRepository.findAll();

    }





    public Club getClubById(Long id) {


        return clubRepository.findById(id)
                .orElseThrow(
                        () -> new ResourceNotFoundException(
                                "Club not found with id: " + id
                        )
                );

    }





    @Transactional
    public Club createClub(Club club) {

        return clubRepository.save(club);

    }





    @Transactional
    public Club updateClub(
            Long id,
            Club updatedClub
    ) {


        Club existingClub = getClubById(id);



        existingClub.setClubName(
                updatedClub.getClubName()
        );



        existingClub.setShortName(
                updatedClub.getShortName()
        );



        existingClub.setCity(
                updatedClub.getCity()
        );



        existingClub.setLogo(
                updatedClub.getLogo()
        );



        existingClub.setDescription(
                updatedClub.getDescription()
        );



        existingClub.setTrophies(
                updatedClub.getTrophies()
        );



        return clubRepository.save(existingClub);

    }





    @Transactional
    public void deleteClub(Long id) {


        Club club = getClubById(id);


        clubRepository.delete(club);

    }


}