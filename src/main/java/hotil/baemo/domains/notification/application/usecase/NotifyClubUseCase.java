package hotil.baemo.domains.notification.application.usecase;

import hotil.baemo.domains.notification.domains.value.club.ClubId;
import hotil.baemo.domains.notification.domains.value.club.ClubPostId;
import hotil.baemo.domains.notification.domains.value.community.PostTitle;
import hotil.baemo.domains.notification.domains.value.community.ThumbnailText;
import hotil.baemo.domains.notification.domains.value.user.UserId;

public interface NotifyClubUseCase {

    void notifyApplyingToClubManagers(ClubId clubId, UserId targetUserId);

    void notifyApproveToTargetUser(ClubId clubId, UserId targetUserId);

    void notifyJoinToClubManagers(ClubId clubId, UserId userId);

    void notifyExpelledToTargetUser(ClubId clubId, UserId targetUserId);

    void notifyPostCreationToClubMembers(ClubId clubId, ClubPostId clubPostId, UserId targetUserId, PostTitle postTitle, ThumbnailText thumbnailText);

    void notifyPostCommentedToWriter(ClubId clubId, ClubPostId clubPostId, UserId targetUserId, PostTitle postTitle, ThumbnailText thumbnailText);

    void notifyLeftToClubManagers(ClubId clubId, UserId userId);

}
