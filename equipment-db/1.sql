--changeset ahull@sungevity:1

create table equipment_type (
  id serial primary key,
  name varchar(255) default null
);

create table manufacturer (
  id serial primary key,
  name varchar(255) default null
);

create table equipment (
  id serial primary key,
  equipment_type_id int not null references equipment_type(id),
  manufacturer_id int not null references manufacturer(id),
  model varchar(255) not null,
  description varchar(255) default null,
  rating int default null,
  panel_kw_stc double precision default null,
  panel_kw_ptc double precision default null,
  panel_height_mm double precision default null,
  panel_width_mm double precision default null,
  inverter_efficiency double precision default null,
  inverter_output_voltage double precision default null,
  inverter_is_three_phase smallint default null,
  panel_is_bipv_rated double precision default null,
  power_temp_coefficient double precision default null,
  normal_operating_cell_temperature double precision default null,
  median_pmax_multiplier double precision default null,
  date_median_pmax_updated date default null,
  pmax_notes varchar(255) default null,
  panel_area double precision default null,
  modified_date timestamp not null default CURRENT_TIMESTAMP,
  is_archived smallint default null
);

CREATE OR REPLACE FUNCTION update_modified_date_column() RETURNS TRIGGER AS '
  BEGIN
    NEW.modified_date = NOW();
    RETURN NEW;
  END;
' LANGUAGE 'plpgsql';

CREATE TRIGGER update_modified_date BEFORE UPDATE
  ON equipment FOR EACH ROW EXECUTE PROCEDURE
  update_modified_date_column();