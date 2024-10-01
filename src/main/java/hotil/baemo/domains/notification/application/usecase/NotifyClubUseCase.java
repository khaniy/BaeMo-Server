package hotil.baemo.domains.notification.application.usecase;

import hotil.baemo.domains.notification.domains.value.club.ClubId;
import hotil.baemo.domains.notification.domains.value.club.ClubTitle;
import hotil.baemo.domains.notification.domains.value.community.PostTitle;
import hotil.baemo.domains.notification.domains.value.community.ThumbnailText;
import hotil.baemo.domains.notification.domains.value.user.UserId;

public interface NotifyClubUseCase {

    void notifyApplyingToClubAdmins(ClubId clubId, UserId targetUserId);

    void notifyApproveToTargetUser(ClubId clubId, UserId targetUserId);

    void notifyExpelledToTargetUser(ClubId clubId, UserId targetUserId);

    void notifyPostCreationToClubMembers(ClubId clubId, ClubTitle title, UserId targetUserId, PostTitle postTitle, ThumbnailText thumbnailText);

    void notifyPostRepliedToWriter(ClubId clubId, ClubTitle title, UserId targetUserId, PostTitle postTitle, ThumbnailText thumbnailText);
}
