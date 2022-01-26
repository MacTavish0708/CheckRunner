# CheckRunner

ИНСТРУКЦИЯ


для компиляции:
javac -d bin src/CheckRunner.java

для запуска

java -cp ./bin CheckRunner

После CheckRunner записываются наборы параметров с которыми запускается программа
например

java -cp ./bin CheckRunner 50-6 1-2 2-25 3-4 4-50 5-6 6-7 7-8 8-50 9-1 10-999 11-999 12-4 13-55 14-999 15-7 16-8 17-9 18-25 19-2 20-50 21-4 22-5 23-6 24-7 25-8 26-999 27-1 28-2 29-3 30-4 31-5 32-6 33-7 34-8 0-9 card-999 productsFile.txt auctionFile.txt cardsFile.txt

Параметры представлют собой записанные через пробел данные: число1-число2 : число1 это id продукта, число2 это его количество.
id представляет собой номер товара в документе productsFile.txt 

Товары в документе записываются следующим образом:
название двоеточие цена товара, например:

carrot:123.456

potato:789.123

card-ХХХХ : ХХХХ-четырехзначное число кода скидочной карты
Набор карточек задается в файле cardsFile.txt
В файле задаются карточка и их скидка через тире, например:

1111-5

2222-6

auctionFile.txt - файл в котором указываются id аукционных товаров, напрмер:

1

2

3

4


После выполнения программы результат выводится на консоль и в файл fileOut.txt
Если файлы откуда брать данные не указаны, то будут использовать дефолтные данные которые прописаны в коде программы.
Если файл указан неправильно, то будет напечатано уведомление об этом в консоль, что также повлечет использования дефолтных данных.






ТЕКСТ УСЛОВИЯ
1. Разработать консольное приложение, реализующее функционал формирования чека в магазине.

2. Приложение запускается java RunnerClassName <набор_параметров>, где набор параметров в формате itemId-quantity (itemId - идентификатор товара, quantity - его количество.
Например: java CheckRunner 3-1 2-5 5-1 card-1234 должен сформировать и вывести в консоль чек содержащий в себе наименование товара с id=3 в количестве 1шт, то же самое с id=2 в количестве 5 штук, id=5 - одна штука и т. д. Card-1234 означает, что была предъявлена скидочная карта с номером 1234. Необходимо вывести в консоль сформированный чек (вариант на рисунке), содержащий в себе список товаров и их количество с ценой, а также рассчитанную сумму с учетом скидки по предъявленной карте (если она есть).

3. Среди товаров, предусмотреть акционные. Если их в чеке больше пяти, то сделать скидку 10% по этой позиции. Данную информацию отразить в чеке.

4. Набор товаров и скидочных карт может задаваться прямо в коде, массивом или коллекцией объектов. Их количество и номенклатура имеет тестовый характер, поэтому наименование и количество свободные.

5. Готовое задание можно передать zip архивом с исходным кодом (набор .java файлов), которые можно скомпилировать при помощи javac и полученный .class файл запустить через java. Можно использовать сборщики (gradle, maven, ant и пр.). В любом случае, лучше приложить инструкцию по запуску вашего приложения. Кроме того, кодовую базу можно разместить в любом из публичных репозиториев (Bitbucket, github, gitlab).

6. В данном задание важно показать понимание ООП, умение строить модели (выделять классы, интерфейсы, их связи), разделять функционал между ними  а также знать синтаксис самого языка. Обратить внимание на устойчивость к изменениям в логике и избегать копипаста. Только после этого можно перейти к необязательным заданиям отмеченных *.

7. * Организовать чтение исходных данных (товары и скидочные карты) из файлов (в таком случае, можно передавать имя файла в набор параметров команды java)/.

8. * Реализовать вывод чека в файл.

9. * Реализовать обработку исключений (например, товара с id или файла не существует  и т. д.).

10. * Покрыть функционал тестами
