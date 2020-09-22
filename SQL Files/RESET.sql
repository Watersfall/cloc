USE cloc;
UPDATE nation_stats SET economic=DEFAULT, gdp=DEFAULT, growth=DEFAULT, budget=DEFAULT, iron=DEFAULT, coal=DEFAULT, oil=DEFAULT,
                        food=DEFAULT, steel=DEFAULT, nitrogen=DEFAULT, research=DEFAULT, recent_deconscription=DEFAULT,
                        recent_conscription=DEFAULT, land=DEFAULT, government=DEFAULT, approval=DEFAULT, stability=DEFAULT,
                        rebels=DEFAULT, lost_manpower=DEFAULT, months_in_famine=DEFAULT, war_protection=DEFAULT,
                        army_size=DEFAULT, army_training=DEFAULT, fortification=DEFAULT, casualties=DEFAULT,
                        alignment=DEFAULT, entente_reputation=DEFAULT, central_powers_reputation=DEFAULT;

UPDATE nation_producibles SET zeppelins=DEFAULT, bombers=DEFAULT, recon_balloons=DEFAULT, recon_planes=DEFAULT,
							  biplane_fighters=DEFAULT, triplane_fighters=DEFAULT, monoplane_fighters=DEFAULT,
							  musket=DEFAULT, rifled_musket=DEFAULT, single_shot=DEFAULT, needle_nose=DEFAULT,
							  bolt_action_manual=DEFAULT, bolt_action_clip=DEFAULT, straight_pull=DEFAULT,semi_auto=DEFAULT,
							  machine_gun=DEFAULT, artillery=DEFAULT, tank=DEFAULT;

UPDATE nation_tech SET chem_tech=DEFAULT, advanced_chem_tech=DEFAULT, bomber_tech=DEFAULT, strategic_bombing_tech=DEFAULT,
					   tank_tech=DEFAULT, ship_oil_tech=DEFAULT, bolt_action_manual_tech=DEFAULT,
					   semi_automatic_tech=DEFAULT, machine_gun_tech=DEFAULT, food_tech=DEFAULT, ball_and_powder_tech=DEFAULT,
					   belt_tech=DEFAULT, brass_cartridge_tech=DEFAULT, detachable_magazines_tech=DEFAULT,
					   paper_cartridge_tech=DEFAULT, rifle_clips_tech=DEFAULT, smokeless_powder_tech=DEFAULT,
					   bolt_action_clip_tech=DEFAULT, needle_nose_rifle_tech=DEFAULT, rifled_musket_tech=DEFAULT,
					   single_shot_rifle_tech=DEFAULT, straight_pull_rifle_tech=DEFAULT, recon_balloon_tech=DEFAULT,
					   recon_plane_tech=DEFAULT, zeppelin_bombers_tech=DEFAULT, biplane_fighter_tech=DEFAULT,
					   triplane_fighter_tech=DEFAULT, monoplane_fighter_tech=DEFAULT, musket_tech=DEFAULT,
					   artillery_tech=DEFAULT, basic_trenches_tech=DEFAULT, advanced_trenches_tech=DEFAULT,
					   basic_fortifications_tech=DEFAULT, reinforced_concrete_tech=DEFAULT, mobile_defense_tech=DEFAULT,
					   basic_artificial_fertilizer=DEFAULT, artificial_fertilizer=DEFAULT,
					   advanced_artificial_fertilizer=DEFAULT, farming_machines=DEFAULT, advanced_farming_machines=DEFAULT;

UPDATE nation_policy SET manpower_policy=DEFAULT, manpower_change=DEFAULT, food_policy=DEFAULT, food_change=DEFAULT,
						 economy_policy=DEFAULT, economy_change=DEFAULT, fortification_change=DEFAULT,
						 fortification_policy=DEFAULT, farming_subsidies=DEFAULT, farming_subsidies_change=DEFAULT;

DELETE FROM cities WHERE capital=FALSE;
UPDATE cities SET railroads=DEFAULT, ports=DEFAULT, barracks=DEFAULT, iron_mines=DEFAULT, coal_mines=DEFAULT,
                       oil_wells=DEFAULT, civilian_industry=DEFAULT, nitrogen_industry=DEFAULT, universities=DEFAULT,
                       devastation=DEFAULT, population=DEFAULT;

DELETE FROM alignments_transactions;
DELETE FROM alignments_equipment;
DELETE FROM anti_spam;
DELETE FROM declarations;
DELETE FROM events;
DELETE FROM factories;
DELETE FROM global_stats_history;
DELETE FROM modifiers;
DELETE FROM news;
DELETE FROM production;
DELETE FROM treaty_members;
DELETE FROM treaty_invites;
DELETE FROM treaties;
DELETE FROM war_logs;
DELETE FROM wars;
UPDATE main SET month=0, day=0;