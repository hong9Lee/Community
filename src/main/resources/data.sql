#
#
# insert into user (`user_id`, `nick_name`, `account_id`, `account_type`, `public_name`, `created_at`, `updated_at`, `quit`) values (1, 'honggyu', '1', 'LESSOR', 'honggyu(임대인)',now(), now(), 'N');
# insert into user (`user_id`, `nick_name`, `account_id`, `account_type`, `public_name`,`created_at`, `updated_at`, `quit`) values (2, 'rich', '2', 'REALTOR', 'rich(공인 중개사)',now(), now(), 'N');
# insert into user (`user_id`, `nick_name`, `account_id`, `account_type`, `public_name`,`created_at`, `updated_at`, `quit`) values (3, 'jason', '3', 'LESSEE', 'jason(임차인)',now(), now(), 'N');
# insert into user (`user_id`, `nick_name`, `account_id`, `account_type`, `public_name`,`created_at`, `updated_at`, `quit`) values (4, 'drake', '4', 'REALTOR', 'drake(공인 중계사)',now(), now(), 'N');
# insert into user (`user_id`, `nick_name`, `account_id`, `account_type`, `public_name`,`created_at`, `updated_at`, `quit`) values (5, 'mike', '5', 'LESSEE', 'mike(임차인)',now(), now(), 'N');
#
# insert into community_item (`id`, `title`, `contents`, `user_id`, `created_at`, `updated_at`, `like_count`) values (1, '1. 임대인 사용후기','임대인에게 좋아요', 1, now(), now(), 0);
# insert into community_item (`id`, `title`, `contents`, `user_id`, `created_at`, `updated_at`, `like_count`) values (2, '2. 임차인 사용후기 입니다.','임차인에게 좋아요', 3, now(), now(), 0);
# insert into community_item (`id`, `title`, `contents`, `user_id`, `created_at`, `updated_at`, `like_count`) values (3, '3. 임차인 인데 잘 쓰고 있습니다.','임차인이 쓰기 좋아요', 3, now(), now(), 0);
# insert into community_item (`id`, `title`, `contents`, `user_id`, `created_at`, `updated_at`, `like_count`) values (4, '4. 공인중개사 사용후기 입니다.','공인 중개사에게 좋아요', 4, now(), now(), 0);
# insert into community_item (`id`, `title`, `contents`, `user_id`, `created_at`, `updated_at`, `like_count`) values (5, '5. 만족합니다.','잘 쓰고 있습니다.', 5, now(), now(), 0);
#
# insert into comments(`id`, `contents`, `community_item_id`, `user_id`, `created_at`, `updated_at`) values ( 1, '앞으로 자주 이용할 것 같아요 ~', 1, 1, now(), now());
# insert into comments(`id`, `contents`, `community_item_id`, `user_id`, `created_at`, `updated_at`) values ( 2, '앞으로 자주 이용할 것 같아요 ~', 1, 1, now(), now());
# insert into comments(`id`, `contents`, `community_item_id`, `user_id`, `created_at`, `updated_at`) values ( 3, '앞으로 자주 이용할 것 같아요 ~', 2, 2, now(), now());
# insert into comments(`id`, `contents`, `community_item_id`, `user_id`, `created_at`, `updated_at`) values ( 4, '앞으로 자주 이용할 것 같아요 ~', 5, 3, now(), now());
#
# insert into likes(`likes_id`, `community_item_id`, `user_id`, `created_at`, `updated_at`) values (1, 1, 1, now(), now());
# insert into likes(`likes_id`, `community_item_id`, `user_id`, `created_at`, `updated_at`) values (2, 1, 2, now(), now());
# insert into likes(`likes_id`, `community_item_id`, `user_id`, `created_at`, `updated_at`) values (3, 2, 1, now(), now());
# insert into likes(`likes_id`, `community_item_id`, `user_id`, `created_at`, `updated_at`) values (4, 4, 3, now(), now());
# insert into likes(`likes_id`, `community_item_id`, `user_id`, `created_at`, `updated_at`) values (5, 5, 4, now(), now());
#
