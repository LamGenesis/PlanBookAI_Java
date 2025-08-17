-- scripts/plan-task-sample-data.sql
-- Sample data cho Plan Service và Task Service (ĐÃ SỬA TRIỆT ĐỂ)

-- ==============================================
-- PLAN SERVICE DATA
-- ==============================================

-- Lesson Templates (Mẫu giáo án)
INSERT INTO content.lesson_templates (title, description, template_content, subject, grade, created_by) 
VALUES 
-- Template cho lớp 10
('Template Hóa học 10 - Cơ bản', 'Mẫu giáo án cơ bản cho môn Hóa học lớp 10', 
 '{
   "sections": [
     {"name": "I. Mục tiêu bài học", "content": "1. Kiến thức 2. Kỹ năng 3. Thái độ"},
     {"name": "II. Chuẩn bị", "content": "1. Giáo viên 2. Học sinh"},
     {"name": "III. Hoạt động dạy học", "content": "1. Hoạt động khởi động 2. Hoạt động hình thành kiến thức 3. Hoạt động luyện tập 4. Hoạt động vận dụng"},
     {"name": "IV. Củng cố - Dặn dò", "content": "1. Củng cố 2. Bài tập về nhà"}
   ]
 }', 
 'CHEMISTRY', 10, 
 (SELECT id FROM users.users WHERE email = 'admin@planbookai.dev' LIMIT 1)),

-- Template cho lớp 11
('Template Hóa học 11 - Nâng cao', 'Mẫu giáo án nâng cao cho môn Hóa học lớp 11', 
 '{
   "sections": [
     {"name": "I. Mục tiêu bài học", "content": "1. Về kiến thức 2. Về kỹ năng 3. Về phẩm chất"},
     {"name": "II. Thiết bị - Dụng cụ", "content": "1. Hóa chất 2. Dụng cụ thí nghiệm"},
     {"name": "III. Tiến trình bài học", "content": "1. Ổn định lớp 2. Kiểm tra bài cũ 3. Bài mới 4. Củng cố"},
     {"name": "IV. Đánh giá", "content": "1. Đánh giá quá trình 2. Đánh giá kết quả"}
   ]
 }', 
 'CHEMISTRY', 11, 
 (SELECT id FROM users.users WHERE email = 'admin@planbookai.dev' LIMIT 1)),

-- Template cho lớp 12
('Template Hóa học 12 - Ôn thi', 'Mẫu giáo án ôn thi cho môn Hóa học lớp 12', 
 '{
   "sections": [
     {"name": "I. Mục tiêu", "content": "1. Kiến thức trọng tâm 2. Kỹ năng giải bài tập 3. Định hướng thi cử"},
     {"name": "II. Nội dung ôn tập", "content": "1. Lý thuyết cơ bản 2. Bài tập điển hình 3. Đề thi mẫu"},
     {"name": "III. Phương pháp", "content": "1. Thảo luận nhóm 2. Giải bài tập 3. Hỏi đáp"},
     {"name": "IV. Bài tập về nhà", "content": "1. Làm đề thi thử 2. Ôn lý thuyết"}
   ]
 }', 
 'CHEMISTRY', 12, 
 (SELECT id FROM users.users WHERE email = 'admin@planbookai.dev' LIMIT 1));

-- Sample Lesson Plans (Giáo án mẫu)
INSERT INTO content.lesson_plans (title, objectives, content, subject, grade, teacher_id, template_id, status) 
VALUES 
('Bài 1: Cấu tạo nguyên tử', 
 'Học sinh nắm được cấu tạo nguyên tử, các hạt cơ bản trong nguyên tử',
 '{
   "I. Mục tiêu bài học": {
     "kien_thuc": "Biết được cấu tạo nguyên tử gồm hạt nhân và vỏ electron",
     "ky_nang": "Xác định số proton, neutron, electron trong nguyên tử",
     "thai_do": "Hứng thú học tập, tự giác trong học tập"
   },
   "II. Chuẩn bị": {
     "giao_vien": "Bảng tuần hoàn, mô hình nguyên tử",
     "hoc_sinh": "SGK, vở ghi, đồ dùng học tập"
   },
   "III. Hoạt động dạy học": {
     "khoi_dong": "Đặt câu hỏi: Nguyên tử được cấu tạo như thế nào?",
     "hinh_thanh": "Giảng giải cấu tạo nguyên tử với hạt nhân và electron",
     "luyen_tap": "Bài tập xác định số hạt trong nguyên tử H, He, Li",
     "van_dung": "Áp dụng vào các nguyên tố khác trong bảng tuần hoàn"
   }
 }',
 'CHEMISTRY', 10,
 (SELECT id FROM users.users WHERE email = 'admin@planbookai.dev' LIMIT 1),
 (SELECT id FROM content.lesson_templates WHERE title = 'Template Hóa học 10 - Cơ bản' LIMIT 1),
 'PUBLISHED'),

('Bài 5: Liên kết cộng hóa trị', 
 'Học sinh hiểu được liên kết cộng hóa trị và các tính chất',
 '{
   "I. Mục tiêu bài học": {
     "kien_thuc": "Nắm được khái niệm, đặc điểm liên kết cộng hóa trị",
     "ky_nang": "Viết được công thức Lewis của các phân tử đơn giản",
     "thai_do": "Tích cực tham gia thảo luận nhóm"
   },
   "II. Chuẩn bị": {
     "giao_vien": "Mô hình phân tử, phiếu học tập",
     "hoc_sinh": "Ôn lại bài cấu tạo nguyên tử"
   },
   "III. Hoạt động dạy học": {
     "khoi_dong": "Nhắc lại liên kết ion, đặt vấn đề liên kết khác",
     "hinh_thanh": "Giải thích cơ chế hình thành liên kết cộng hóa trị",
     "luyen_tap": "Viết công thức Lewis cho H2, HCl, H2O, NH3",
     "van_dung": "Dự đoán tính chất của các chất có liên kết cộng hóa trị"
   }
 }',
 'CHEMISTRY', 10,
 (SELECT id FROM users.users WHERE email = 'admin@planbookai.dev' LIMIT 1),
 (SELECT id FROM content.lesson_templates WHERE title = 'Template Hóa học 10 - Cơ bản' LIMIT 1),
 'DRAFT');

-- ==============================================
-- TASK SERVICE DATA  
-- ==============================================

-- Sample Questions (Ngân hàng câu hỏi)
INSERT INTO assessment.questions (content, type, difficulty, subject, topic, correct_answer, explanation, created_by) 
VALUES 
-- EASY Questions
('Nguyên tử gồm có những hạt cơ bản nào?', 'MULTIPLE_CHOICE', 'EASY', 'CHEMISTRY', 'Cấu tạo nguyên tử', 'A', 
 'Nguyên tử gồm có 3 hạt cơ bản: proton (+), neutron (không mang điện), electron (-)',
 (SELECT id FROM users.users WHERE email = 'admin@planbookai.dev' LIMIT 1)),

('Điện tích của proton là bao nhiêu?', 'MULTIPLE_CHOICE', 'EASY', 'CHEMISTRY', 'Cấu tạo nguyên tử', 'B',
 'Proton mang điện tích dương +1',
 (SELECT id FROM users.users WHERE email = 'admin@planbookai.dev' LIMIT 1)),

('Electron có khối lượng như thế nào so với proton?', 'MULTIPLE_CHOICE', 'EASY', 'CHEMISTRY', 'Cấu tạo nguyên tử', 'C',
 'Khối lượng electron rất nhỏ, gần như bằng 0 so với proton',
 (SELECT id FROM users.users WHERE email = 'admin@planbookai.dev' LIMIT 1)),

-- MEDIUM Questions  
('Nguyên tố X có Z = 11. Cấu hình electron của X là?', 'MULTIPLE_CHOICE', 'MEDIUM', 'CHEMISTRY', 'Cấu hình electron', 'A',
 'Z = 11 có nghĩa là có 11 electron. Cấu hình: 1s²2s²2p⁶3s¹',
 (SELECT id FROM users.users WHERE email = 'admin@planbookai.dev' LIMIT 1)),

('Ion X²⁺ có cấu hình electron là 1s²2s²2p⁶. Nguyên tố X thuộc nhóm nào?', 'MULTIPLE_CHOICE', 'MEDIUM', 'CHEMISTRY', 'Bảng tuần hoàn', 'B',
 'X²⁺ có 10e, nên X có 12e. Cấu hình X: 1s²2s²2p⁶3s² → nhóm IIA',
 (SELECT id FROM users.users WHERE email = 'admin@planbookai.dev' LIMIT 1)),

-- HARD Questions
('Trong phản ứng: aAl + bHNO₃ → cAl(NO₃)₃ + dNO + eH₂O. Tỉ lệ a:b là?', 'MULTIPLE_CHOICE', 'HARD', 'CHEMISTRY', 'Phản ứng oxi hóa khử', 'C',
 'Cân bằng: Al → Al³⁺ + 3e; N⁺⁵ + 3e → N⁺². Tỉ lệ a:b = 1:4',
 (SELECT id FROM users.users WHERE email = 'admin@planbookai.dev' LIMIT 1)),

-- ESSAY Questions
('Giải thích tại sao kim loại kiềm có tính khử mạnh?', 'ESSAY', 'MEDIUM', 'CHEMISTRY', 'Kim loại kiềm', NULL,
 'Kim loại kiềm có 1 electron ở lớp ngoài cùng, dễ nhường electron để đạt cấu hình bền vững của khí hiếm',
 (SELECT id FROM users.users WHERE email = 'admin@planbookai.dev' LIMIT 1)),

('So sánh liên kết ion và liên kết cộng hóa trị về cơ chế hình thành và tính chất', 'ESSAY', 'HARD', 'CHEMISTRY', 'Liên kết hóa học', NULL,
 'Liên kết ion: cho nhận electron, tạo ion trái dấu hút nhau. Liên kết cộng hóa trị: dùng chung cặp electron',
 (SELECT id FROM users.users WHERE email = 'admin@planbookai.dev' LIMIT 1));

-- Question Choices (Lựa chọn cho câu trắc nghiệm)
INSERT INTO assessment.question_choices (question_id, choice_order, content) 
VALUES 
-- Câu 1: Nguyên tử gồm có những hạt cơ bản nào?
((SELECT id FROM assessment.questions WHERE content = 'Nguyên tử gồm có những hạt cơ bản nào?' LIMIT 1), 'A', 'Proton, neutron, electron'),
((SELECT id FROM assessment.questions WHERE content = 'Nguyên tử gồm có những hạt cơ bản nào?' LIMIT 1), 'B', 'Proton, electron'),
((SELECT id FROM assessment.questions WHERE content = 'Nguyên tử gồm có những hạt cơ bản nào?' LIMIT 1), 'C', 'Neutron, electron'), 
((SELECT id FROM assessment.questions WHERE content = 'Nguyên tử gồm có những hạt cơ bản nào?' LIMIT 1), 'D', 'Chỉ có proton'),

-- Câu 2: Điện tích của proton
((SELECT id FROM assessment.questions WHERE content = 'Điện tích của proton là bao nhiêu?' LIMIT 1), 'A', '0'),
((SELECT id FROM assessment.questions WHERE content = 'Điện tích của proton là bao nhiêu?' LIMIT 1), 'B', '+1'),
((SELECT id FROM assessment.questions WHERE content = 'Điện tích của proton là bao nhiêu?' LIMIT 1), 'C', '-1'),
((SELECT id FROM assessment.questions WHERE content = 'Điện tích của proton là bao nhiêu?' LIMIT 1), 'D', '+2'),

-- Câu 3: Khối lượng electron
((SELECT id FROM assessment.questions WHERE content = 'Electron có khối lượng như thế nào so với proton?' LIMIT 1), 'A', 'Bằng proton'),
((SELECT id FROM assessment.questions WHERE content = 'Electron có khối lượng như thế nào so với proton?' LIMIT 1), 'B', 'Gấp đôi proton'),
((SELECT id FROM assessment.questions WHERE content = 'Electron có khối lượng như thế nào so với proton?' LIMIT 1), 'C', 'Rất nhỏ, gần như bằng 0'),
((SELECT id FROM assessment.questions WHERE content = 'Electron có khối lượng như thế nào so với proton?' LIMIT 1), 'D', 'Bằng neutron'),

-- Câu 4: Cấu hình electron Z=11
((SELECT id FROM assessment.questions WHERE content = 'Nguyên tố X có Z = 11. Cấu hình electron của X là?' LIMIT 1), 'A', '1s²2s²2p⁶3s¹'),
((SELECT id FROM assessment.questions WHERE content = 'Nguyên tố X có Z = 11. Cấu hình electron của X là?' LIMIT 1), 'B', '1s²2s²2p⁶3p¹'),
((SELECT id FROM assessment.questions WHERE content = 'Nguyên tố X có Z = 11. Cấu hình electron của X là?' LIMIT 1), 'C', '1s²2s²2p⁵3s²'),
((SELECT id FROM assessment.questions WHERE content = 'Nguyên tố X có Z = 11. Cấu hình electron của X là?' LIMIT 1), 'D', '1s²2s²2p⁶3d¹'),

-- Câu 5: Ion X²⁺
((SELECT id FROM assessment.questions WHERE content = 'Ion X²⁺ có cấu hình electron là 1s²2s²2p⁶. Nguyên tố X thuộc nhóm nào?' LIMIT 1), 'A', 'Nhóm IA'),
((SELECT id FROM assessment.questions WHERE content = 'Ion X²⁺ có cấu hình electron là 1s²2s²2p⁶. Nguyên tố X thuộc nhóm nào?' LIMIT 1), 'B', 'Nhóm IIA'), 
((SELECT id FROM assessment.questions WHERE content = 'Ion X²⁺ có cấu hình electron là 1s²2s²2p⁶. Nguyên tố X thuộc nhóm nào?' LIMIT 1), 'C', 'Nhóm IIIA'),
((SELECT id FROM assessment.questions WHERE content = 'Ion X²⁺ có cấu hình electron là 1s²2s²2p⁶. Nguyên tố X thuộc nhóm nào?' LIMIT 1), 'D', 'Nhóm VIIA'),

-- Câu 6: Phản ứng oxi hóa khử
((SELECT id FROM assessment.questions WHERE content = 'Trong phản ứng: aAl + bHNO₃ → cAl(NO₃)₃ + dNO + eH₂O. Tỉ lệ a:b là?' LIMIT 1), 'A', '1:3'),
((SELECT id FROM assessment.questions WHERE content = 'Trong phản ứng: aAl + bHNO₃ → cAl(NO₃)₃ + dNO + eH₂O. Tỉ lệ a:b là?' LIMIT 1), 'B', '1:5'),
((SELECT id FROM assessment.questions WHERE content = 'Trong phản ứng: aAl + bHNO₃ → cAl(NO₃)₃ + dNO + eH₂O. Tỉ lệ a:b là?' LIMIT 1), 'C', '1:4'),
((SELECT id FROM assessment.questions WHERE content = 'Trong phản ứng: aAl + bHNO₃ → cAl(NO₃)₃ + dNO + eH₂O. Tỉ lệ a:b là?' LIMIT 1), 'D', '3:4');

-- Sample Exams (Đề thi mẫu)
INSERT INTO assessment.exams (title, description, subject, grade, duration_minutes, total_score, teacher_id, status)
VALUES 
('Kiểm tra 15 phút - Cấu tạo nguyên tử', 'Kiểm tra ngắn về cấu tạo nguyên tử và cấu hình electron', 
 'CHEMISTRY', 10, 15, 10.00,
 (SELECT id FROM users.users WHERE email = 'admin@planbookai.dev' LIMIT 1), 'PUBLISHED'),

('Kiểm tra 1 tiết - Liên kết hóa học', 'Kiểm tra về liên kết ion và liên kết cộng hóa trị',
 'CHEMISTRY', 10, 45, 10.00,
 (SELECT id FROM users.users WHERE email = 'admin@planbookai.dev' LIMIT 1), 'DRAFT');

-- Exam Questions (Câu hỏi trong đề thi)
INSERT INTO assessment.exam_questions (exam_id, question_id, question_order, points)
VALUES 
-- Đề kiểm tra 15 phút
((SELECT id FROM assessment.exams WHERE title = 'Kiểm tra 15 phút - Cấu tạo nguyên tử' LIMIT 1), 
 (SELECT id FROM assessment.questions WHERE content = 'Nguyên tử gồm có những hạt cơ bản nào?' LIMIT 1), 1, 2.00),
((SELECT id FROM assessment.exams WHERE title = 'Kiểm tra 15 phút - Cấu tạo nguyên tử' LIMIT 1), 
 (SELECT id FROM assessment.questions WHERE content = 'Điện tích của proton là bao nhiêu?' LIMIT 1), 2, 2.00),
((SELECT id FROM assessment.exams WHERE title = 'Kiểm tra 15 phút - Cấu tạo nguyên tử' LIMIT 1), 
 (SELECT id FROM assessment.questions WHERE content = 'Nguyên tố X có Z = 11. Cấu hình electron của X là?' LIMIT 1), 3, 3.00),
((SELECT id FROM assessment.exams WHERE title = 'Kiểm tra 15 phút - Cấu tạo nguyên tử' LIMIT 1), 
 (SELECT id FROM assessment.questions WHERE content = 'Ion X²⁺ có cấu hình electron là 1s²2s²2p⁶. Nguyên tố X thuộc nhóm nào?' LIMIT 1), 4, 3.00);

-- Sample Classes và Students
INSERT INTO students.classes (name, grade, student_count, homeroom_teacher_id, academic_year)
VALUES 
('10A1', 10, 35, (SELECT id FROM users.users WHERE email = 'admin@planbookai.dev' LIMIT 1), '2024-2025'),
('10A2', 10, 32, (SELECT id FROM users.users WHERE email = 'admin@planbookai.dev' LIMIT 1), '2024-2025');

-- Sample Students (một vài học sinh mẫu)
INSERT INTO students.students (full_name, student_code, class_id, birth_date, gender, owner_teacher_id)
VALUES 
('Nguyễn Văn An', 'HS001', (SELECT id FROM students.classes WHERE name = '10A1' LIMIT 1), '2008-05-15', 'MALE',
 (SELECT id FROM users.users WHERE email = 'admin@planbookai.dev' LIMIT 1)),
('Trần Thị Bình', 'HS002', (SELECT id FROM students.classes WHERE name = '10A1' LIMIT 1), '2008-07-20', 'FEMALE', 
 (SELECT id FROM users.users WHERE email = 'admin@planbookai.dev' LIMIT 1)),
('Lê Hoàng Cường', 'HS003', (SELECT id FROM students.classes WHERE name = '10A1' LIMIT 1), '2008-03-10', 'MALE',
 (SELECT id FROM users.users WHERE email = 'admin@planbookai.dev' LIMIT 1));