INSERT INTO `type` (`type`, `id`, `code`, `title`, `description`) VALUES
('ActivityState', 1, 'K', 'ToDo', 'Its need to be done.'),
('ActivityState', 2, 'H', 'Trash', 'Well in truth its not needed.'),
('ActivityState', 3, 'A', 'Archived', 'I dont know how it could be done. So maybe later.'),
('ActivityState', 4, 'O', 'Postpone', 'Maybe later'),
('ActivityState', 5, 'Z', 'Done', 'Done!'),
('PersonState', 6, 'A', 'Active', 'Acive account'),
('PersonState', 7, 'N', 'Inactive', 'Inactive account'),
('ContactType', 8, 'E', 'Email', 'E-mail address'),
('ContactType', 9, 'T', 'Telephone', 'Telephone number'),
('ProjectState', 10, 'A','Active','Active project.'),
('ProjectState', 11, 'D','Done','Its done.'),
('TaskState', 12, 'V','Created','My new task.'),
('TaskState', 13, 'A','Active','I am working on it!'),
('TaskState', 14, 'K','In calendar','I know time when to do it.'),
('TaskState', 15, 'H','Done','Done!'),
('NoteState', 16, 'A','Active','Active note'),
('NoteState', 17, 'D','Deleted','Deleted note - will not show.'),
('NoteState', 18, 'H','Hide','Hide note - will not be automatically unpacked.');

/*stav_id -> state_id*/
INSERT INTO `person` (`name`, `login`, `password`, `surname`, `right_generate_token`, `state_id`)
	VALUES ('Michal Sláma', 'slamamic', md5('heslo'), 'Sláma', 1, 6) ;
