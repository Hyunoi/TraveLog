package com.example.travelog.domain.travel.entity;

import com.example.travelog.domain.user.entity.User;
import com.example.travelog.global.entity.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
@Builder
@SQLDelete(sql = "UPDATE travel SET deleted_at = CURRENT_TIMESTAMP WHERE id = ?")
@Where(clause = "deleted_at IS NULL")
public class Travel extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    private String title;
    private String description;
    private String location;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private String thumbnailUrl;

    public void updateTravel(String title,
                             String description,
                             LocalDateTime startTime,
                             LocalDateTime endTime) {
        this.title = title;
        this.description = description;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public void updateThumbnailImage(String imageUrl) {
        this.thumbnailUrl = imageUrl;
    }

    public void deleteThumbnailImage() {
        this.thumbnailUrl = null;
    }
}
