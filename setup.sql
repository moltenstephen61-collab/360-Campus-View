-- Connect to or create the target database instance
CONNECT 'jdbc:derby://localhost:1527/ClassList;create=true' USER 'app' PASSWORD '123';

-- Structure definition for campus coordinates
CREATE TABLE Location (
    id          INT PRIMARY KEY,
    name        VARCHAR(100) NOT NULL,
    image_path  VARCHAR(255) NOT NULL
);

-- Insert location logs ordered by picture sequences
INSERT INTO APP.Location (id, name, image_path) VALUES 
(1, 'Outdoor Front Gate', 'images/panoramas/outdoors/1-outdoor-front-gate.jpg'),
(2, 'Outdoor Cafeteria', 'images/panoramas/outdoors/2-outdoor-cafeteria.jpg'),
(3, 'Outdoor Benches 1', 'images/panoramas/outdoors/3-outdoor-benches-1.jpg'),
(4, 'Outdoor Benches 2', 'images/panoramas/outdoors/4-outdoor-benches-2.jpg'),
(5, 'Court 1', 'images/panoramas/outdoors/5-outdoor-court-1.jpg'),
(6, 'Outdoor Benches 3', 'images/panoramas/outdoors/6-outdoor-benches-3.jpg'),
(7, 'Court 2', 'images/panoramas/outdoors/7-outdoor-court-2.jpg'),
(8, 'Outdoor Hall', 'images/panoramas/outdoors/8-outdoor-hall.jpg'),
(9, 'Clinic Entrance', 'images/panoramas/outdoors/9-outdoor-clinic.jpg'),
(10, 'Outdoor side', 'images/panoramas/outdoors/10-outdoor-front-entrance.jpg'),
(11, 'Staff Entrance Gate Side', 'images/panoramas/outdoors/11-outdoor-side.jpg'),
(12, 'Main Entrance', 'images/panoramas/outdoors/12-outdoor-main-entrance.jpg'),
(13, 'Outdoor Hub', 'images/panoramas/outdoors/13-outdoor-hub.jpg');