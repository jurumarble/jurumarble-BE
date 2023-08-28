package co.kr.jurumarble.vote.service;

import co.kr.jurumarble.bookmark.repository.BookmarkRepository;
import co.kr.jurumarble.drink.domain.DrinkFinder;
import co.kr.jurumarble.drink.domain.dto.DrinkIdsUsedForVote;
import co.kr.jurumarble.drink.domain.dto.DrinksUsedForVote;
import co.kr.jurumarble.drink.domain.entity.Drink;
import co.kr.jurumarble.user.domain.User;
import co.kr.jurumarble.user.repository.UserRepository;
import co.kr.jurumarble.vote.domain.Vote;
import co.kr.jurumarble.vote.domain.VoteContent;
import co.kr.jurumarble.vote.domain.VoteDrinkContent;
import co.kr.jurumarble.vote.domain.VoteGenerator;
import co.kr.jurumarble.vote.enums.VoteType;
import co.kr.jurumarble.vote.repository.VoteRepository;
import co.kr.jurumarble.vote.repository.VoteResultRepository;
import co.kr.jurumarble.vote.service.request.CreateDrinkVoteServiceRequest;
import co.kr.jurumarble.vote.service.request.CreateNormalVoteServiceRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class VoteServiceTest {

    @InjectMocks
    private VoteService voteService;

    @Mock
    private UserRepository userRepository;
    @Mock
    private VoteRepository voteRepository;
    @Mock
    private VoteResultRepository voteResultRepository;
    @Mock
    private BookmarkRepository bookmarkRepository;
    @Mock
    private VoteGenerator voteGenerator;

    @Mock
    private DrinkFinder drinkFinder;

    @DisplayName("일반 투표를 생성한다.")
    @Test
    void createVote() {
        // given
        Long userId = 1L;

        User user = User.builder()
                .build();

        CreateNormalVoteServiceRequest request = CreateNormalVoteServiceRequest.builder()
                .title("투표 제목")
                .titleA("A 타이틀")
                .titleB("B 타이틀")
                .imageA("A 이미지")
                .imageB("B 이미지")
                .voteType(VoteType.NORMAL)
                .build();
        VoteContent voteContent = request.toVoteContent();

        Vote vote = request.toVote(userId);


        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        // when
        voteService.createNormalVote(request, userId);

        // then
        verify(voteGenerator, times(1)).createNormalVote(vote, voteContent);
    }


    @DisplayName("전통주 투표를 생성한다.")
    @Test
    void createDrinkVote() {
        // given
        Long userId = 1L;
        Long drinkAId = 1L;
        Long drinkBId = 2L;

        User user = User.builder()
                .build();

        CreateDrinkVoteServiceRequest request = CreateDrinkVoteServiceRequest.builder()
                .title("투표 제목")
                .drinkAId(drinkAId)
                .drinkBId(drinkBId)
                .voteType(VoteType.DRINK)
                .build();

        Drink drinkA = Drink.builder()
                .id(drinkAId)
                .build();
        Drink drinkB = Drink.builder()
                .id(drinkBId)
                .build();

        DrinksUsedForVote drinksUsedForVote = new DrinksUsedForVote(drinkA, drinkB);

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(drinkFinder.findDrinksUsedForVote(any(DrinkIdsUsedForVote.class)))
                .thenReturn(drinksUsedForVote);

        VoteDrinkContent voteDrinkContent = VoteDrinkContent.createFromDrinks(drinksUsedForVote);
        Vote vote = request.toVote(userId);

        // when
        voteService.createDrinkVote(request, userId);

        // then
        verify(voteGenerator, times(1)).createDrinkVote(vote, voteDrinkContent);
    }
}