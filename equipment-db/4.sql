--liquibase formatted sql

--changeset efattig@sungevity:4

INSERT INTO manufacturer (id,name)
VALUES
  (62, 'Solis'),
  (63, 'CSUN');

INSERT INTO equipment (id,equipment_type_id,manufacturer_id,model,description,rating,panel_kw_stc,panel_kw_ptc,panel_height_mm,panel_width_mm,inverter_efficiency,inverter_output_voltage,inverter_is_three_phase,panel_is_bipv_rated,power_temp_coefficient,normal_operating_cell_temperature,median_pmax_multiplier,date_median_pmax_updated,pmax_notes,panel_area,modified_date)
VALUES
  (15036,'1','62','Mini 1000','1000W(230)',1000,NULL,NULL,NULL,NULL,0.962,230,NULL,NULL,NULL,NULL,NULL,date(now()),NULL,NULL,now()),
  (15037,'1','62','Mini 1500','1500W(230)',1500,NULL,NULL,NULL,NULL,0.962,230,NULL,NULL,NULL,NULL,NULL,date(now()),NULL,NULL,now()),
  (15038,'1','62','Mini 2000','2000W(230)',2000,NULL,NULL,NULL,NULL,0.962,230,NULL,NULL,NULL,NULL,NULL,date(now()),NULL,NULL,now()),
  (15039,'1','62','Mini 2500','2500W(230)',2500,NULL,NULL,NULL,NULL,0.962,230,NULL,NULL,NULL,NULL,NULL,date(now()),NULL,NULL,now()),
  (15040,'1','62','3K-2G','3000W(230)',3000,NULL,NULL,NULL,NULL,0.968,230,NULL,NULL,NULL,NULL,NULL,date(now()),NULL,NULL,now()),
  (15041,'1','62','4K-2G','4000W(230)',4000,NULL,NULL,NULL,NULL,0.964,230,NULL,NULL,NULL,NULL,NULL,date(now()),NULL,NULL,now()),
  (15042,'1','62','5K-2G','5000W(230)',5000,NULL,NULL,NULL,NULL,0.965,230,NULL,NULL,NULL,NULL,NULL,date(now()),NULL,NULL,now()),
  (15043,'1','62','2.5K-2G','2500W(230 2/2)',2500,NULL,NULL,NULL,NULL,0.968,230,NULL,NULL,NULL,NULL,NULL,date(now()),NULL,NULL,now()),
  (15044,'1','62','3.6K-2G','3600W(230)',3600,NULL,NULL,NULL,NULL,0.964,230,NULL,NULL,NULL,NULL,NULL,date(now()),NULL,NULL,now()),
  (15045,'1','62','6K','6000W(230)',6000,NULL,NULL,NULL,NULL,0.968,230,NULL,NULL,NULL,NULL,NULL,date(now()),NULL,NULL,now()),
  (15046,'1','62','10K','10000W (380)',10000,NULL,NULL,NULL,NULL,0.968,380,NULL,NULL,NULL,NULL,NULL,date(now()),NULL,NULL,now()),
  (15047,'2','63','260-60','260W Polycrystalline Module',NULL,0.26,0.192,1640,990,NULL,NULL,NULL,NULL,-0.408,45,1.015,date(now()),NULL,1.6236,now()),
  (15048,'2','13','JAM6(R)-(BK)-260Wp','260W All Black Monocrystalline Module',NULL,0.26,0.19006,1650,991,NULL,NULL,NULL,NULL,-0.41,45,1.015,date(now()),NULL,1.63515,now());

--rollback delete from equipment where id in (15036,15037,15038,15039,15040,15041,15042,15043,15044,15045,15046,15047,15048);
--rollback delete from manufacturer where id in (62,63);
