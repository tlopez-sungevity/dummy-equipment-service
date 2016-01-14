--liquibase formatted sql

--changeset ahull@sungevity:3

alter table equipment alter column inverter_is_three_phase drop default;
alter table equipment alter inverter_is_three_phase type boolean using 
  case 
    when inverter_is_three_phase is null then null 
    when inverter_is_three_phase=0 then false 
    else true 
  end;
alter table equipment alter column inverter_is_three_phase set default null;

alter table equipment alter column is_archived drop default;
alter table equipment alter is_archived type boolean using 
  case 
    when is_archived is null then null 
    when is_archived=0 then false 
    else true 
  end;
alter table equipment alter column is_archived set default null;

--rollback alter table equipment alter column inverter_is_three_phase drop default;
--rollback alter table equipment alter inverter_is_three_phase type smallint using case when inverter_is_three_phase is null then null when inverter_is_three_phase=false then 0 else 1 end;
--rollback alter table equipment alter column inverter_is_three_phase set default null;

--rollback alter table equipment alter column is_archived drop default;
--rollback alter table equipment alter is_archived type smallint using case when is_archived is null then null when is_archived=false then 0 else 1 end;
--rollback alter table equipment alter column is_archived set default null;