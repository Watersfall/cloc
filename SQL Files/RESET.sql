USE cloc;
UPDATE cloc_army SET size=DEFAULT, training=DEFAULT, musket=DEFAULT, rifled_musket=DEFAULT, single_shot=DEFAULT,
                     needle_nose=DEFAULT, bolt_action_manual=DEFAULT, bolt_action_clip=DEFAULT, straight_pull=DEFAULT,
                     semi_auto=DEFAULT, machine_gun=DEFAULT, artillery=DEFAULT, fortification=DEFAULT, tank=DEFAULT;
DELETE FROM cloc_cities WHERE capital=FALSE;
UPDATE cloc_cities SET railroads=DEFAULT, ports=DEFAULT, barracks=DEFAULT, iron_mines=DEFAULT, coal_mines=DEFAULT,
                       oil_wells=DEFAULT, civilian_industry=DEFAULT, nitrogen_industry=DEFAULT, universities=DEFAULT,
                       devastation=DEFAULT, population=DEFAULT, months_on_strike=DEFAULT, strike_level=DEFAULT;
DELETE FROM cloc_declarations;
UPDATE cloc_domestic SET land=DEFAULT, approval=DEFAULT, stability=DEFAULT, rebels=DEFAULT, lost_manpower=DEFAULT,
                         farm_subsidies=DEFAULT, farm_regulations=DEFAULT, farm_technology=DEFAULT,
                         farm_collectivization=DEFAULT, months_in_famine=DEFAULT;
UPDATE cloc_economy SET economic=DEFAULT, gdp=DEFAULT, growth=DEFAULT, budget=DEFAULT, iron=DEFAULT, coal=DEFAULT,
                        oil=DEFAULT, food=DEFAULT, steel=DEFAULT, nitrogen=DEFAULT, research=DEFAULT,
                        recent_conscription=DEFAULT, recent_deconscription=DEFAULT;
UPDATE cloc_foreign SET alignment=DEFAULT;
UPDATE cloc_login SET last_login=0;
UPDATE cloc_main SET month=0, day=0;
UPDATE cloc_military SET zeppelins=DEFAULT, bombers=DEFAULT, submarines=DEFAULT, destroyers=DEFAULT, cruisers=DEFAULT,
                         pre_battleships=DEFAULT, battleships=DEFAULT, transports=DEFAULT, war_protection=DEFAULT,
                         recon_balloons=DEFAULT, recon_planes=DEFAULT, biplane_fighters=DEFAULT,
                         triplane_fighters=DEFAULT, monoplane_fighters=DEFAULT;
DELETE FROM cloc_news;
UPDATE cloc_policy SET manpower_policy=DEFAULT, manpower_change=DEFAULT, food_policy=DEFAULT, food_change=DEFAULT,
                       economy_policy=DEFAULT, economy_change=DEFAULT, fortification_change=DEFAULT,
                       fortification_policy=DEFAULT, farming_subsidies=DEFAULT, farming_subsidies_change=DEFAULT;
UPDATE cloc_tech SET chem_tech=DEFAULT, advanced_chem_tech=DEFAULT, bomber_tech=DEFAULT, strategic_bombing_tech=DEFAULT,
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
DELETE FROM cloc_treaties_members;
DELETE FROM cloc_treaty_invites;
DELETE FROM cloc_treaties;
DELETE FROM cloc_war;
DELETE FROM cloc_war_logs;
DELETE FROM events;
DELETE FROM factories;
DELETE FROM global_stats_history;
DELETE FROM production;
DELETE FROM modifiers;
DELETE FROM nation_history;
DELETE FROM anti_spam;