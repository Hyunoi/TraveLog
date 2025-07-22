package com.example.travelog.domain.photo.entity;

import com.example.travelog.domain.travel.entity.Travel;
import com.example.travelog.global.entity.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
@Builder
@SQLDelete(sql = "UPDATE travel SET deleted_at = CURRENT_TIMESTAMP WHERE id = ?")
@Where(clause = "deleted_at IS NULL")
public class Photo extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "travel_id", nullable = false)
    private Travel travel;

    @Column(length = 1000)
    private String photoUrl;

    private String location;
    private String comment;

    public void updatePhoto(String comment,
                       String photoUrl,
                       String location) {
        this.comment = comment;
        this.photoUrl = photoUrl;
        this.location = location;
    }
}
