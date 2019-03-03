# Emergency-Room-Simulator
Data inceperii: 23/12/2018
Data finalizarii: 06/01/2019

Implementare:
	# Main - realizeaza citirea din fisierele de tip JSON cu ajutorul ObjectMapper
	       - si simularea dorita folosind elementele de mai jos
	# Compar - Singleton Pattern
		 - comparator folosit pentru a sorta in functie de urgenta, severitate si nume
	# Doctor - obiectul ce reprezinta un doctor
	# Hospital - Singleton Pattern
	           - triage() :
			* realizeaza trecerea pacientilor care au venit la spital in acea zi in lista de triage
			* sorteaza pacientii in functie de severitate
			* in functie de cate asistenta sunt disponibile, calculeaza urgenta aceluiasi nr de pacienti
			* si ii trimit la examinare
			* eliminam din lista pacientii trimisi la examinare
		   - examine() :
			* sorteaza pacientii cu ajutorul comparatorului facut
			* fiecarui pacient i se atribuie un doctor care este compatibil cu boala lui
			* daca nu este gasit nici un doctor, pacientul este transferat la alt spital
			* in functie de starea pacientilor
					- NOT_DIAGNOSED - sunt consultati, iar daca au severitatea <= maxForTreatmentul 
							doctorului asignat va pleca acasa, daca nu, va fi trimis la invesigatii
					- TREATMENT - pacientii veniti de la investigatii si au rezultatul treatment, pleaca acasa
					- OPERATE/HOSPITALIZE - acestia sunt operati sau nu, dupa care sunt spitalizati
							      - in urma acestor actiuni, le este actualizata severitatea
							      si recalculat numarul de zile ce trebuie sa stea la spital,
							      adaugandu-i in lista cu cei spitalizati
			* eliminam din lista pacientii ce au fost trimisi la investigatii/spitalizati/trimisi acasa
		   - investigate() :
			* sorteaza pacientii cu ajutorul comparatorului facut
			* in fucntie de cati ER Tehnician sunt disponibili, calculeaza rezultatul
			* si ii trimit inapoi in lista de examinare si ii elimina
	# Patient - obiectul ce reprezinta un pacient
		  - compatible() :
			* decide daca un doctor este compatibil cu boala pacientului
		  - sortByName() :
			* sorteaza o lista cu pacienti alfabetic dupa numele lor
		  - sortByDone() :
			* sorteaza o lista cu pacienti dupa statusul Done
	# Printer - observatorul lui Hospital
		  - afiseaza mesajele cerute folosindu-se de listele printPatients, hospitalized si doctors
