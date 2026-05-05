-- ============================================
-- Google Ads 连接器 - PostgreSQL 数据库表结构
-- 版本: 1.0
-- 创建时间: 2025-05-04
-- ============================================

-- ============================================
-- 1. Google Ads 连接配置表
-- ============================================
DROP TABLE IF EXISTS google_ads_customer_match CASCADE;
DROP TABLE IF EXISTS google_ads_sync_log CASCADE;
DROP TABLE IF EXISTS google_ads_store CASCADE;

CREATE TABLE google_ads_store (
    -- 主键
    id BIGSERIAL PRIMARY KEY,

    -- Google Ads 配置信息
    developer_token VARCHAR(500) NOT NULL,
    client_customer_id VARCHAR(100) NOT NULL,
    client_id VARCHAR(500) NOT NULL,
    client_secret VARCHAR(500) NOT NULL,
    refresh_token VARCHAR(1000) NOT NULL,

    -- 连接信息
    manager_account_id VARCHAR(100),
    login_customer_id VARCHAR(100),

    -- 状态信息
    enabled BOOLEAN DEFAULT TRUE,
    sync_status VARCHAR(20),
    last_sync_time TIMESTAMP,
    last_error_message TEXT,

    -- 审计字段
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    create_by VARCHAR(50),
    update_by VARCHAR(50),

    -- 约束
    CONSTRAINT uk_google_ads_customer UNIQUE (client_customer_id)
);

-- 索引
CREATE INDEX idx_google_ads_store_enabled ON google_ads_store(enabled) WHERE enabled = TRUE;
CREATE INDEX idx_google_ads_store_sync_status ON google_ads_store(sync_status);
CREATE INDEX idx_google_ads_store_create_time ON google_ads_store(create_time DESC);

-- 注释
COMMENT ON TABLE google_ads_store IS 'Google Ads 连接配置表';
COMMENT ON COLUMN google_ads_store.id IS '主键 ID';
COMMENT ON COLUMN google_ads_store.developer_token IS 'Google Ads 开发者令牌';
COMMENT ON COLUMN google_ads_store.client_customer_id IS '客户 ID (格式: 123-456-7890)';
COMMENT ON COLUMN google_ads_store.client_id IS 'OAuth2 客户端 ID';
COMMENT ON COLUMN google_ads_store.client_secret IS 'OAuth2 客户端密钥';
COMMENT ON COLUMN google_ads_store.refresh_token IS 'OAuth2 刷新令牌';
COMMENT ON COLUMN google_ads_store.manager_account_id IS '经理账户 ID';
COMMENT ON COLUMN google_ads_store.login_customer_id IS '登录客户 ID';
COMMENT ON COLUMN google_ads_store.enabled IS '是否启用';
COMMENT ON COLUMN google_ads_store.sync_status IS '同步状态: pending/syncing/success/failed';
COMMENT ON COLUMN google_ads_store.last_sync_time IS '最后同步时间';
COMMENT ON COLUMN google_ads_store.last_error_message IS '最后错误信息';


-- ============================================
-- 2. Google Ads 客户匹配数据表
-- ============================================
DROP TABLE IF EXISTS google_ads_customer_match CASCADE;

CREATE TABLE google_ads_customer_match (
    -- 主键
    id BIGSERIAL PRIMARY KEY,

    -- 关联信息
    google_ads_store_id BIGINT NOT NULL,

    -- 用户标识
    user_id VARCHAR(100),

    -- 哈希后的用户数据（SHA-256 加密）
    hashed_email VARCHAR(128),
    hashed_phone VARCHAR(128),
    hashed_first_name VARCHAR(128),
    hashed_last_name VARCHAR(128),
    hashed_street_address VARCHAR(128),
    hashed_city VARCHAR(128),
    hashed_state VARCHAR(128),
    hashed_postal_code VARCHAR(128),
    hashed_country_code VARCHAR(128),

    -- 移动设备 ID
    mobile_device_id VARCHAR(128),

    -- 用户数据（原始格式，用于加密）
    raw_email VARCHAR(500),
    raw_phone VARCHAR(50),
    raw_first_name VARCHAR(100),
    raw_last_name VARCHAR(100),
    raw_street_address VARCHAR(500),
    raw_city VARCHAR(100),
    raw_state VARCHAR(100),
    raw_postal_code VARCHAR(20),
    raw_country_code VARCHAR(10),

    -- 同步状态
    sync_status VARCHAR(20) DEFAULT 'pending',
    sync_time TIMESTAMP,
    error_message TEXT,

    -- 审计字段
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    -- 外键约束
    CONSTRAINT fk_google_ads_customer_match_store FOREIGN KEY (google_ads_store_id)
        REFERENCES google_ads_store(id) ON DELETE CASCADE
);

-- 索引
CREATE INDEX idx_google_ads_customer_match_store_id ON google_ads_customer_match(google_ads_store_id);
CREATE INDEX idx_google_ads_customer_match_user_id ON google_ads_customer_match(user_id);
CREATE INDEX idx_google_ads_customer_match_sync_status ON google_ads_customer_match(sync_status);
CREATE INDEX idx_google_ads_customer_match_hashed_email ON google_ads_customer_match(hashed_email);
CREATE INDEX idx_google_ads_customer_match_mobile_device_id ON google_ads_customer_match(mobile_device_id);
CREATE INDEX idx_google_ads_customer_match_create_time ON google_ads_customer_match(create_time DESC);

-- 注释
COMMENT ON TABLE google_ads_customer_match IS 'Google Ads 客户匹配数据表';
COMMENT ON COLUMN google_ads_customer_match.id IS '主键 ID';
COMMENT ON COLUMN google_ads_customer_match.google_ads_store_id IS '关联的 Google Ads 连接 ID';
COMMENT ON COLUMN google_ads_customer_match.user_id IS '用户 ID';
COMMENT ON COLUMN google_ads_customer_match.hashed_email IS 'SHA-256 加密后的邮箱';
COMMENT ON COLUMN google_ads_customer_match.hashed_phone IS 'SHA-256 加密后的手机号';
COMMENT ON COLUMN google_ads_customer_match.hashed_first_name IS 'SHA-256 加密后的名字';
COMMENT ON COLUMN google_ads_customer_match.hashed_last_name IS 'SHA-256 加密后的姓氏';
COMMENT ON COLUMN google_ads_customer_match.hashed_street_address IS 'SHA-256 加密后的街道地址';
COMMENT ON COLUMN google_ads_customer_match.hashed_city IS 'SHA-256 加密后的城市';
COMMENT ON COLUMN google_ads_customer_match.hashed_state IS 'SHA-256 加密后的州/省';
COMMENT ON COLUMN google_ads_customer_match.hashed_postal_code IS 'SHA-256 加密后的邮编';
COMMENT ON COLUMN google_ads_customer_match.hashed_country_code IS 'SHA-256 加密后的国家代码';
COMMENT ON COLUMN google_ads_customer_match.mobile_device_id IS '移动设备 ID (IDFA 或 AdID)';
COMMENT ON COLUMN google_ads_customer_match.raw_email IS '原始邮箱';
COMMENT ON COLUMN google_ads_customer_match.raw_phone IS '原始手机号';
COMMENT ON COLUMN google_ads_customer_match.raw_first_name IS '原始名字';
COMMENT ON COLUMN google_ads_customer_match.raw_last_name IS '原始姓氏';
COMMENT ON COLUMN google_ads_customer_match.raw_street_address IS '原始街道地址';
COMMENT ON COLUMN google_ads_customer_match.raw_city IS '原始城市';
COMMENT ON COLUMN google_ads_customer_match.raw_state IS '原始州/省';
COMMENT ON COLUMN google_ads_customer_match.raw_postal_code IS '原始邮编';
COMMENT ON COLUMN google_ads_customer_match.raw_country_code IS '原始国家代码';
COMMENT ON COLUMN google_ads_customer_match.sync_status IS '同步状态: pending/syncing/success/failed';
COMMENT ON COLUMN google_ads_customer_match.sync_time IS '同步时间';
COMMENT ON COLUMN google_ads_customer_match.error_message IS '错误信息';


-- ============================================
-- 3. Google Ads 同步日志表
-- ============================================
DROP TABLE IF EXISTS google_ads_sync_log CASCADE;

CREATE TABLE google_ads_sync_log (
    -- 主键
    id BIGSERIAL PRIMARY KEY,

    -- 关联信息
    google_ads_store_id BIGINT NOT NULL,

    -- 同步信息
    sync_type VARCHAR(50) NOT NULL,
    sync_status VARCHAR(20) NOT NULL,

    -- 时间信息
    start_time TIMESTAMP,
    end_time TIMESTAMP,

    -- 统计信息
    success_count BIGINT DEFAULT 0,
    failure_count BIGINT DEFAULT 0,
    total_count BIGINT DEFAULT 0,

    -- 错误信息
    error_message TEXT,

    -- 执行耗时
    duration BIGINT,

    -- 审计字段
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    -- 外键约束
    CONSTRAINT fk_google_ads_sync_log_store FOREIGN KEY (google_ads_store_id)
        REFERENCES google_ads_store(id) ON DELETE CASCADE
);

-- 索引
CREATE INDEX idx_google_ads_sync_log_store_id ON google_ads_sync_log(google_ads_store_id);
CREATE INDEX idx_google_ads_sync_log_sync_type ON google_ads_sync_log(sync_type);
CREATE INDEX idx_google_ads_sync_log_sync_status ON google_ads_sync_log(sync_status);
CREATE INDEX idx_google_ads_sync_log_start_time ON google_ads_sync_log(start_time DESC);
CREATE INDEX idx_google_ads_sync_log_create_time ON google_ads_sync_log(create_time DESC);

-- 复合索引
CREATE INDEX idx_google_ads_sync_log_store_time ON google_ads_sync_log(google_ads_store_id, start_time DESC);

-- 注释
COMMENT ON TABLE google_ads_sync_log IS 'Google Ads 同步日志表';
COMMENT ON COLUMN google_ads_sync_log.id IS '主键 ID';
COMMENT ON COLUMN google_ads_sync_log.google_ads_store_id IS '关联的 Google Ads 连接 ID';
COMMENT ON COLUMN google_ads_sync_log.sync_type IS '同步类型: customer_match(客户匹配)';
COMMENT ON COLUMN google_ads_sync_log.sync_status IS '同步状态: pending/running/success/failed/partial_success';
COMMENT ON COLUMN google_ads_sync_log.start_time IS '开始时间';
COMMENT ON COLUMN google_ads_sync_log.end_time IS '结束时间';
COMMENT ON COLUMN google_ads_sync_log.success_count IS '成功记录数';
COMMENT ON COLUMN google_ads_sync_log.failure_count IS '失败记录数';
COMMENT ON COLUMN google_ads_sync_log.total_count IS '总记录数';
COMMENT ON COLUMN google_ads_sync_log.error_message IS '错误信息';
COMMENT ON COLUMN google_ads_sync_log.duration IS '执行耗时（毫秒）';


-- ============================================
-- 4. 自动更新触发器
-- ============================================

-- 创建更新时间自动更新函数
CREATE OR REPLACE FUNCTION update_google_ads_updated_time_column()
RETURNS TRIGGER AS $$
BEGIN
    NEW.update_time = CURRENT_TIMESTAMP;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

-- 为 google_ads_store 表创建触发器
CREATE TRIGGER trigger_google_ads_store_update_time
BEFORE UPDATE ON google_ads_store
FOR EACH ROW
EXECUTE FUNCTION update_google_ads_updated_time_column();

-- 为 google_ads_customer_match 表创建触发器
CREATE TRIGGER trigger_google_ads_customer_match_update_time
BEFORE UPDATE ON google_ads_customer_match
FOR EACH ROW
EXECUTE FUNCTION update_google_ads_updated_time_column();


-- ============================================
-- 结束
-- ============================================
