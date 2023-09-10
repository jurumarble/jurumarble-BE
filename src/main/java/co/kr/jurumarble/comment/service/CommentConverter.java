package co.kr.jurumarble.comment.service;

import co.kr.jurumarble.comment.domain.Comment;
import co.kr.jurumarble.comment.enums.CommentType;
import co.kr.jurumarble.comment.repository.CommentRepository;
import co.kr.jurumarble.comment.service.request.GetCommentServiceRequest;
import co.kr.jurumarble.user.enums.ChoiceType;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class CommentConverter {
    private final CommentVoteService commentVoteService;
    private final CommentRepository commentRepository;


    public List<GetCommentData> convertToCommentDataList(List<Comment> parentComments, CommentType commentType, Long typeId) {
        List<GetCommentData> getCommentData = new ArrayList<>();
        Map<Long, GetCommentData> map = new HashMap<>();

        for (Comment comment : parentComments) {
            ChoiceType choice = commentVoteService.getChoiceType(comment, commentType, typeId);

            GetCommentData response = new GetCommentData(comment, choice);
            map.put(response.getId(), response);

            if (response.getParentId() != null) {
                map.get(response.getParentId()).getChildren().add(response);
            } else {
                getCommentData.add(response);
            }
        }

        return getCommentData;
    }


    public Slice<GetCommentData> convertToSlice(Long voteId, GetCommentServiceRequest request, Pageable pageable, List<GetCommentData> getCommentData) {
        int countComments = commentRepository.countByVoteIdAndParentIsNull(voteId);   //투표에 속한 부모 댓글수 전부 가져오기
        int lastPageNumber = (int) Math.ceil((double) countComments / request.getSize());
        boolean hasNext = request.getPage() < lastPageNumber - 1;

        Slice<GetCommentData> slice = new SliceImpl<>(getCommentData, pageable, hasNext);
        return slice;
    }

}
