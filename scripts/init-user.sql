CREATE DATABASE planbookai_user;
\connect planbookai_user;

CREATE SCHEMA IF NOT EXISTS profiles;

CREATE TABLE IF NOT EXISTS profiles.user_profiles (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    auth_user_id UUID NOT NULL, -- Tham chiếu từ planbookai_auth.users.users.id
    avatar_url TEXT,
    bio TEXT,
    phone_number VARCHAR(20),
    address TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
