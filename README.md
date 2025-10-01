# 🛡️ Správa evidence pojištěných 

Plně responzivní webová aplikace pro správu pojištěných osob, jejich pojistných smluv a událostí. Projekt vznikl jako rozšíření znalostí získaných v akreditovaném kurzu, s důrazem na backendovou čistotu, systémovou čitelnost a bezpečnostní návrh.

---

## ⚙️ Tech-stack

| Kategorie             | Technologie                                      |
|----------------------|--------------------------------------------------|
| Back-end             | Java, Spring Boot, Spring Security, Hibernate, Maven |
| Front-end            | HTML, CSS, Bootstrap, Thymeleaf                  |
| Validace & Mapování  | Jakarta Bean Validation, MapStruct              |
| Databázová vrstva    | MySQL (XAMPP)                                    |
| UI & Utility Pluginy | Lightbox                                         |

---

## 🧩 Funkce

- ✅ Kompletní CRUD pojištěných osob  
- ✅ Kompletní CRUD smluv a pojistných událostí  
- ✅ Kompletní CRUD novinek a uživatelů aplikace  
- 🗃️ Správa archivu smazaných entit  
- 👥 Podpora uživatelských rolí: administrátor, manažer, zaměstnanec, novinář  
- 🔄 Rozlišení rolí: pojistník (plátce) vs. pojištěnec (osoba, na kterou se pojištění vztahuje)  
- 📊 Generování statistik ve formě reportů  
- 🧙‍♂️ Wizard form: skládání komplexních údajů přes vícestupňové formuláře  
- 📱 Plná responzivita: od flash-messages po validaci pod formulářovými poli  
- 🛡️ Validace vstupů a defenzivní programování proti externím útokům  

---

## 🎯 Smysl projektu

Cílem bylo nejen procvičit principy webového vývoje, ale hlavně ověřit schopnost navrhnout a realizovat aplikaci bez zbytečné abstrakce. Projekt staví na:

- Důrazu na backendovou čistotu  
- Systémové čitelnosti  
- Bezpečnostním principech  
- Architektonické modularitě  

Aplikace je připravena růst — s podporou rolí, statistik, archivace a zaměnitelného jádra.

---

## 🚀 Spuštění projektu

```bash
# Backend
mvn spring-boot:run

# Frontend
# Spouští se automaticky přes Spring Boot + Thymeleaf

# Databáze
# MySQL přes XAMPP, nutné upravit přístupové údaje v application.properties
