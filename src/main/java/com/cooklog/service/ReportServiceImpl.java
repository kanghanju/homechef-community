package com.cooklog.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.cooklog.dto.ReportedContentDTO;
import com.cooklog.model.Blacklist;
import com.cooklog.model.Board;
import com.cooklog.model.Comment;
import com.cooklog.model.Role;
import com.cooklog.model.User;
import com.cooklog.repository.BlacklistRepository;
import com.cooklog.repository.BoardRepository;
import com.cooklog.repository.CommentRepository;
import com.cooklog.repository.UserRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ReportServiceImpl implements ReportService {
	private final UserRepository userRepository;
	private final BlacklistRepository blacklistRepository;
	private final CommentRepository commentRepository;
	private final BoardRepository boardRepository;


	public List<ReportedContentDTO> findReportedContents() {
		List<ReportedContentDTO> reportedContents = new ArrayList<>();
		List<User> reportedUsers = userRepository.findByReportCountGreaterThan(0);

		for (User user : reportedUsers) {
			// 사용자가 블랙리스트에 있는지 여부를 확인
			boolean isBlacklisted = blacklistRepository.findOneByUserIdx(user.getIdx()).isPresent();

			reportedContents.add(new ReportedContentDTO(
				user.getNickname(),
				user.getReportCount(),
				user.getIdx(),
				isBlacklisted // 블랙리스트 여부를 DTO에 설정
			));
		}

		return reportedContents;
	}

	//댓글 신고 기능
	@Override
	public void reportComment(Long commentId) {
		Comment comment = commentRepository.findById(commentId)
			.orElseThrow(() -> new IllegalArgumentException("해당 댓글이 존재하지 않습니다."));

		User user = userRepository.findById(comment.getUser().getIdx())
			.orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));

		user.increaseReportCount();
		userRepository.save(user);
	}
	//게시글 신고 기능
	@Override
	public void reportBoard(Long boardId) {
		Board board = boardRepository.findById(boardId)
			.orElseThrow(() -> new IllegalArgumentException("해당 게시글이 존재하지 않습니다."));

		User user = userRepository.findById(board.getUser().getIdx())
			.orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));

		user.increaseReportCount();
		userRepository.save(user);
	}
}