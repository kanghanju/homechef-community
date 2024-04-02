package com.cooklog.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OrderBy;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Formula;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "board")
public class Board {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_idx")
	private User user;

	private String content;

	@CreatedDate
	@Column(name = "created_at")
	private LocalDateTime createdAt;

	@LastModifiedDate
	@Column(name = "updated_at")
	private LocalDateTime updatedAt;

	@Column(name = "readcnt")
	private Integer readCount;

	@Formula("(select count(*) from likes where likes.board_id = id)")
	private int likesCount;

	@OneToMany(mappedBy = "board")
	@OrderBy("order ASC")
	private List<Image> images = new ArrayList<>();

	@OneToMany(mappedBy = "board")
	private List<Likes> likes = new ArrayList<>();

	@OneToMany(mappedBy = "board")
	@OrderBy("id ASC")
	private List<Tag> tags=new ArrayList<>();


	@OneToMany(mappedBy = "board")
	@OrderBy("id DESC") //최근에 등록된 게시물부터 보여주기 위함
	private List<Bookmark> bookmarks = new ArrayList<>();

	public void update(String content){
		this.content=content;
	}
}
