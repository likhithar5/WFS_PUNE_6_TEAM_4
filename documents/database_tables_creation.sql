# created database for project
create database hsbc_codefury;
use hsbc_codefury;

# creating users table
create table users(
	id integer not null primary key,
    name varchar(60) not null,
    email varchar(200) not null unique,
    role varchar(60) not null,
    last_logged_in datetime
);

# creating table to store meeting credits
create table credits(
	user_id integer not null unique,
    meeting_credits integer not null,
    
    constraint fk_users_to_credits_by_userid foreign key (user_id) references users(id) on delete cascade,
    constraint cap_max_credits_to_2000  check (meeting_credits>=0 and meeting_credits<=2000)
);

# creating table to store phone numbers of users
create table contact_numbers(
	user_id integer not null,
    mobile_no varchar(10) not null unique,
    
    constraint fk_users_to_contactNumber_by_userid foreign key (user_id) references users(id) on delete cascade
);

#create table for meeting rooms
create table meeting_rooms(
	room_name varchar(60) not null unique primary key,
    seating_capacity integer not null,
    ratings decimal(4,2),
    hourly_cost integer not null,
    number_of_metings_held integer
);

# creating table for meetings
create table meetings(
	id integer not null primary key,
    title text not null,
    meeting_type varchar(60) not null,
    meeting_date date not null,
    start_time time not null,
    end_time time not null,
    description text,
    organized_by integer not null,
    assigned_to_meeting_room varchar(60) not null,
    
    constraint fk_meetings_to_meetingRooms_by_roomName foreign key (assigned_to_meeting_room) references meeting_rooms(room_name) on delete cascade,
    constraint check_startTime_lesserThan_endTime check(start_time<end_time)
);

#create table to store participants of a meeting
create table participants(
	user_id integer not null,
    meeting_id integer not null,
    
    constraint fk_users_to_participants_by_userid foreign key (user_id) references users(id) on delete cascade,
    constraint fk_meetings_to_participants_by_meetingId foreign key (meeting_id) references meetings(id) on delete cascade
);

#create table to store meeting bookings
create table bookings(
	id integer not null primary key auto_increment,
    date date not null,
    startTime time not null,
    endTime time not null,
    booked_by integer not null,
    booking_for_meeting_room varchar(60) not null,
    
    constraint fk_users_to_bookings_by_userid foreign key (booked_by) references users(id),
    constraint fk_meetingRoom_to_bookings_by_meetingRoomName foreign key (booking_for_meeting_room) references meeting_rooms(room_name)
);

#create table for amenities of a meeting room
create table amenities(
	meeting_room_name varchar(60) not null,
    amenities varchar(30) not null,
    
    constraint fk_meetingRoom_to_amenities_by_meetingRoomName foreign key (meeting_room_name) references meeting_rooms(room_name) on delete cascade
);