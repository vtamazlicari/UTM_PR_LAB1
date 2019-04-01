# Laboratorul 4 programarea in retea
Protocoalele poștei electronice. Proiectare și programare aplicație client

Pentru server-ul smtp am folosit Quick 'n Easy Mail Server cu protocolul pop3 pentru receptionarea mesajelor.
Am creat clasa FetchingEmail pentru a face conexiunea prin protocolul pop3 cu mail-serverul si a afisa mesajele, iar cu clasa SendEmail 
am facut conexiunea prin protocolul smtp si am trimis mesajele. Pentru a trimite si vizualiza mesajele primite am creat o mica interfata.
Pentru a vizualiza atasamente, se trimite un mail simplu iar fisierul test.txt din proiect va fi trimisa la adresa destinatie, textul
fisierului va aparea in consula.
