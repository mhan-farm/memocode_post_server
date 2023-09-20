INSERT INTO authors(id, username, created, updated, is_deleted, deleted, name, nickname, image_id)
VALUES ('b148b8df-8779-41ab-91be-ed6e6c4811f5', 'admin', NOW(), NOW(), FALSE, NULL, '홍길동', '관리자1',
        'c955d385-5feb-490e-9884-9eaaa3393d57'
        ) ON CONFLICT (username) DO NOTHING;