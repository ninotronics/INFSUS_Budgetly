# INFSUS_Budgetly
Ovo je repozitorij u kojemu su locirane sve potrebne datoteke i kod u vezi domaće zadaće broj 3 iz predmeta INFSUS na FER-u.

Za instalaciju aplikacije potrebno je klonirati repozitorij na vlastito računalo. Preduvjeti za
kloniranje i pokretanje projekta uključuju instaliran git na računalu, otvoren račun na
GitHub platformi te instaliranu najnoviju verziju podrške za Node.js. Nakon što je Node.js
instaliran, automatski ćete dobiti i NPM (Node Package Manager).
Kloniranje projekta:
• git clone https://github.com/ninotronics/INFSUS_Budgetly.git
Ova naredba će klonirati projekt u zadanu mapu.
Koraci za instalaciju klijentskog dijela aplikacije. Potrebno je prijeći u direktorij
kloniranog projekta te u folder gdje je spremljen klijentski dio:
• cd putanja-do-spremljenog-direktorija/frontend/zavrsniFrontend
Pokrenite naredbu za instalaciju svih korištenih paketa:
• npm install
Ova naredba će instalirati sve potrebne pakete definirane u package.json datoteci.
Pokretanje aplikacije:
• npm start
Aplikacija će se pokrenuti lokalno u pregledniku na adresi http://localhost:3000/.
Koraci za instalaciju poslužiteljskog dijela aplikacije. Potrebno je prijeći u direktorij
kloniranog projekta te u folder gdje je spremljen poslužiteljski dio:
• cd putanja-do-spremljenog-direktorija/backend
Moguće ga je pokrenuti ili u razvojnom okruženju ili uz pomoć sljedećih komadni:
• mvn clean install
• mvn spring-boot:run
Kada se poslužitelj pokrene, entiteti definirani u aplikaciji automatski će se stvoriti u bazi
podataka zahvaljujući konfiguracijama i alatima koje koristimo. Korišten je Hibernate,
popularni ORM (Object-Relational Mapping) radni okvir za Java programiranje. Njegova
glavna funkcija je mapiranje Java objekata na relacijske baze podataka i obrnuto, čime se
smanjuje potreba za ručnim pisanjem SQL koda. Postavljen je na opcije „update“ tako da
ako tablice ne postoje u bazi podataka stvara nove, a u slučaju da postoje samo ažurira
vrijednosti.


Za pokretanje unit testova u frontend dijelu (budgetly/budgetlyfront/src/components)->
npx vitest run

Za pokertranje integracijskih testova (cypress) ->
npx cypress run

Za testiranje backend dijela aplikacije (pozicioniranje u direktorij INFSUS)->
mvn test