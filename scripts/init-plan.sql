CREATE DATABASE planbookai_plan;
\connect planbookai_plan;

CREATE SCHEMA IF NOT EXISTS content;

CREATE TABLE IF NOT EXISTS content.lesson_templates (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    title VARCHAR(255) NOT NULL,
    description TEXT,
    template_content JSONB NOT NULL,
    subject VARCHAR(50) NOT NULL,
    grade INTEGER NOT NULL CHECK (grade IN (10, 11, 12)),
    created_by UUID NOT NULL,
    status VARCHAR(20) DEFAULT 'ACTIVE',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS content.lesson_plans (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    title VARCHAR(255) NOT NULL,
    objectives TEXT,
    content JSONB NOT NULL,
    subject VARCHAR(50) NOT NULL,
    grade INTEGER NOT NULL CHECK (grade IN (10, 11, 12)),
    teacher_id UUID NOT NULL,
    template_id UUID REFERENCES content.lesson_templates(id),
    status VARCHAR(20) DEFAULT 'DRAFT',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
