
CREATE TABLE project
(
    id        VARCHAR(36),
    name       VARCHAR NOT NULL,
    description       VARCHAR,
    PRIMARY KEY (id)
);

CREATE TABLE employee_project
(
    employee_id varchar(36),
    project_id varchar(36),
    PRIMARY KEY (employee_id,project_id),
    FOREIGN KEY (employee_id) REFERENCES  employee (id),
    FOREIGN KEY (project_id) REFERENCES  project (id)
);

ALTER TABLE employee ADD email VARCHAR;
ALTER TABLE employee ADD password VARCHAR;

ALTER TABLE task ADD project_id VARCHAR,
    ADD FOREIGN KEY(project_id) REFERENCES project(id);

CREATE TABLE comment
(
    id VARCHAR(36),
    text VARCHAR,
    added_at date,
    employee_id VARCHAR(36),
    task_id VARCHAR(36),
    comment_id VARCHAR(36),
    PRIMARY KEY (id),
    FOREIGN KEY (employee_id) REFERENCES employee (id),
    FOREIGN KEY (task_id) REFERENCES task (id),
    FOREIGN KEY (comment_id) REFERENCES comment (id)
);