CREATE DATABASE lost_and_found;
USE lost_and_found;
CREATE TABLE users (
    user_id INT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(255) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    email VARCHAR(255) UNIQUE,
    phone VARCHAR(20),
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
CREATE TABLE items (
    item_id INT PRIMARY KEY AUTO_INCREMENT,
    user_id INT,
    item_name VARCHAR(255) NOT NULL,
    description TEXT,
    category VARCHAR(255),
    lost_found ENUM('lost', 'found') NOT NULL,
    location VARCHAR(255),
    image_url VARCHAR(255),
    status ENUM('open', 'closed') DEFAULT 'open',
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(user_id)
);
CREATE TABLE claims (
    claim_id INT PRIMARY KEY AUTO_INCREMENT,
    item_id INT,
    claimer_id INT,
    claim_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    status ENUM('pending', 'approved', 'rejected') DEFAULT 'pending',
    message TEXT,
    FOREIGN KEY (item_id) REFERENCES items(item_id),
    FOREIGN KEY (claimer_id) REFERENCES users(user_id)
);
ALTER DATABASE lost_and_found CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci;
ALTER TABLE items CONVERT TO CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
ALTER TABLE claims CONVERT TO CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
ALTER TABLE users CONVERT TO CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

INSERT INTO users (username, password, email, phone) VALUES
('admin', '123456', 'zhangsan@example.com', '13800000001'),
('李四', '123456', 'lisi@example.com', '13900000002'),
('王五', '123456', 'wangwu@example.com', '13700000003'),
('赵六', '123456', 'zhaoliu@example.com', '13600000004'),
('孙七', '123456', 'sunqi@example.com', '13500000005');

-- 插入5条物品数据，包含遗失和找到的物品，使用中文描述
INSERT INTO items (user_id, item_name, description, category, lost_found, location, image_url, status) VALUES
(1, '黑色双肩背包', '内有电脑和一些书籍，遗失在图书馆', '背包', 'lost', '图书馆三楼阅览室', 'http://example.com/image1.jpg', 'open'),
(2, '银色钥匙串', '上面挂着三把钥匙和一个小熊挂件', '钥匙', 'found', '学校食堂门口', 'http://example.com/image2.jpg', 'open'),
(3, '红色钱包', '内有少量现金和几张银行卡', '钱包', 'lost', '操场旁边的草坪', 'http://example.com/image3.jpg', 'open'),
(4, '蓝色保温杯', '杯身上印有卡通图案', '水杯', 'found', '教学楼A座101教室', 'http://example.com/image4.jpg', 'open'),
(5, '一加手机', '黑色，屏幕有裂痕', '手机', 'lost', '校门口的公交车站', 'http://example.com/image5.jpg', 'open');

-- 插入5条认领数据，包含待处理、已批准和已拒绝的认领请求
INSERT INTO claims (item_id, claimer_id, status, message) VALUES
(1, 2, 'pending', '我在图书馆看到一个和你描述很像的背包'),
(2, 3, 'approved', '经过核实，钥匙是我的，谢谢你！'),
(3, 4, 'rejected', '不好意思，你的描述和我的钱包不符'),
(4, 5, 'pending', '我在教学楼捡到一个保温杯，不知道是不是你的'),
(5, 1, 'pending', '我在校门口的公交车站丢了一个手机，和你描述很像');