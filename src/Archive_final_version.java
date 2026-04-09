import java.io.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Scanner;
import java.util.regex.Pattern;
import java.util.Locale;
class Magazine{
    private String name;
    private String author;
    private double cost;
    public Magazine(String name, String author, double cost){
        setName(name);
        setAuthor(author);
        setCost(cost);}
    private void setName(String name){this.name = name;}
    private void setAuthor(String author){this.author = author;}
    private void setCost(double cost){
        this.cost = cost;}
    public String getName(){
        return this.name;}
    public double getCost(){
        return this.cost;}
    public String getAuthor(){
        return this.author;}
    public String toString(){
        return this.name +" " + this.author + " " + this.cost;}
    public String toString2(){
        return this.name +"," + this.author + "," + this.cost;}}
public class Archive_final_version {
    public static String data_1_patch;
    public static String data_2_patch;
    public static String data_3_patch;
    public static String data_4_patch;
    public static Double min_number;
    public static Double max_number = null;
    // ТАБЛИЦА -
    // БЛОК СХЕМА -
    // Проверил всё заново -
    public static void main(String[] args){
        Scanner scanner = new Scanner(System.in);
        try(BufferedReader reader = new BufferedReader(new FileReader("config.csv"))){
            data_2_patch = reader.readLine();
            data_3_patch = reader.readLine();
            data_4_patch = reader.readLine();}
        catch (IOException e) {
//            System.out.println("Ошибка чтения файла сохранения: ");
        }
        // Загрузка названия файлов
        File directory = new File(".");
        System.out.println("Доступные базы данных");
        int cnt = 1;
        ArrayList<String> files = new ArrayList<>();
        for (File file: directory.listFiles()){
            if(file.getName().contains(".csv") && (!file.getName().equals(data_4_patch) &&
                    !file.getName().equals(data_3_patch) && !file.getName().equals(data_2_patch)&&
                    !file.getName().equals("config.csv"))){
                System.out.println(cnt+" — "+file.getName().replace(".csv", ""));
                files.add(file.getName());
                cnt++;
            }
        }
        // Выбор главного файла
        System.out.println("Найдите нужный файл из названий выше и введите его номер\nили введите название 'Списка толстых журналов'");
        if (scanner.hasNextInt()){
            int num = scanner.nextInt();
            if (num <= files.size() && num>0)
                data_1_patch = files.get(num-1);
            else
                data_1_patch = num + ".csv";
        }
        else
            data_1_patch = scanner.nextLine() + ".csv";

        System.out.println("Вы выбрали: "+data_1_patch);
        System.out.println("Список главных редакторов журналов с указанием наименований");
        setFileName(data_2_patch);
        System.out.println("Файл с минимальной ценой, максимальной ценой и средней стоимостью");
        setFileName(data_4_patch);
        rewriteFiles(openFile(data_1_patch));
        while (true){
            System.out.println("""
                
            1 — Отсортировать файл
            2 — Дозаписать данные в файл
            3 — Вывести данные
            4 — Ввести минимальное и максимальное значение цены
            5 — Удалить журнал
            6 — Вывести данные из файла с редакторами журнала и названием
            7 — Вывести самый дешевый журнал, самый дорогой, и среднюю стоимость
            8 — Вывести журналы из указанного вами диапазона цен
            9 — Завершить программу
                """);
            while (!scanner.hasNextInt()){
                System.out.println("Повторите ввод действия");
                scanner.next();
            }
            int move = scanner.nextInt();
            ArrayList<Magazine> mag = new ArrayList<>();
            switch (move){
                case 6: printSmallFile(data_2_patch);break;
                case 7: printSmallFile(data_4_patch);break;
                case 1:
                    System.out.println("""
                        1 – Сортировка названия по "А-Я"
                        2 – Сортировка названия по "Я-А"
                        3 – Сортировка имени автора по "А-Я"
                        4 – Сортировка имени автора по "Я-А"
                        5 – Сортировка по Возрастанию цены
                        6 – Сортировка по убыванию цены""");
                    String pr;
                    while (true){
                        pr = scanner.nextLine();
                        if (Pattern.matches("[123456]+", pr))
                            break;}
                    sort(pr); break;
                case 2:
                    System.out.println("1 – добавить в список из консоли\n 2 – загрузить в список из файла");
                    while (!scanner.hasNextInt()){
                        System.out.println("Повторите ввод");
                        scanner.next();}
                    addFile(scanner.nextInt());
                    break;
                case 3:
                    mag = openFile(data_1_patch);
                    printFile(mag);
                    break;
                case 5:
                    printFile(openFile(data_1_patch));
                    System.out.println("Введите номер, который хоитите удалить");
                    while (!scanner.hasNextInt()){
                        System.out.println("Повторите ввод действия");
                        scanner.next();
                    }
                    deleteMagazine(scanner.nextInt(), mag);
                    break;
                case 8:
                    ArrayList<String> line = printSmallFile(data_3_patch);
                    if(max_number == null)
                        System.out.println(" ");
                    else if(line.size()==0)
                        System.out.println("Нет журналов для данного диапазона цен");
                    else
                        break;
                case 4:
                    if(max_number == null){
                    System.out.println("Файл с товарами которые подходят под диапазон цены");
                    setFileName(data_3_patch);}
                    System.out.println("Введите минимальную цену журнала");
                    while (!scanner.hasNextDouble()){
                        System.out.println("Введено некоректное значение");
                        System.out.println("Введите минимальную цену журнала");
                        scanner.next();
                    }
                    min_number = scanner.nextDouble();
                    System.out.println("Введите максимальную цену журнала");
                    while (!scanner.hasNextDouble()){
                        System.out.println("Введено некоректное значение");
                        System.out.println("Введите максимальную цену журнала");
                        scanner.next();
                    }
                    max_number = scanner.nextDouble();
                    rewriteFiles(openFile(data_1_patch));
                    System.out.println("Данные о журналах обновлены");
                    break;
                case 9: System.exit(0);
                default: System.out.println("Нет доступных действий");
            }


        }
    }
    // ТАБЛИЦА +
    // БЛОК СХЕМА +
    // Проверил всё заново -
    public static void addFile(int move){
        ArrayList<Magazine> magazines = new ArrayList<>();
        try(PrintWriter writer = new PrintWriter(new FileWriter(data_1_patch, true))){
            Scanner scanner = new Scanner(System.in);
            switch (move){
                case 1:
                    String line = "__";
                    while(true){
                        System.out.println("""
                                Введите данные по такому шаблону
                                Название журнала, Фамилия Имя, Цена (без указания валюты)
                                Для подтверждения ввода данных или его завершения нажмите Enter""");
                        line = scanner.nextLine();
                        if (line.isEmpty())
                            break;
                        String[] line_list = line.trim().split(",");
                        if ((line_list.length == 3) && (Pattern.matches("^[^0-9]*$", line_list[1].trim()))){
                            try {
                                magazines.add(new Magazine(line_list[0].trim(), line_list[1].trim(), Math.abs(Double.parseDouble(line_list[2].trim()))));
                            }
                            catch (Exception e){
                                System.out.println("Повторите Ввод, проверьте верно ли вы указали цену журнала");
                            }}
                        else{
                            System.out.println("Повторите Ввод, проверьте все ли данные вы ввели(ввод данных через запятую)");
                        }
                    }break;
                case 2:
                    System.out.println("Введите название файла из которого" +
                            "вы хотите добавить данные (без формата)");
                    magazines = openFile(scanner.nextLine()+"csv");
                    break;
                    // Загрузка названия файлов
//                    File directory = new File(".");
//                    System.out.println("Доступные базы данных");
//                    int cnt = 1;
//                    ArrayList<String> files = new ArrayList<>();
//                    for (File file: directory.listFiles()){
//                        if(file.getName().contains(".csv") && (!file.getName().equals(data_1_patch)) && (!file.getName().equals(data_4_patch) &&
//                                !file.getName().equals(data_3_patch) && !file.getName().equals(data_2_patch)&&
//                                !file.getName().equals("config.csv"))){
//                            System.out.println(cnt+" — "+file.getName().replace(".csv", ""));
//                            files.add(file.getName());
//                            cnt++;
//                        }
//                    }
//                    System.out.println("Введите номер БД из которой вы хотите загрузить файл");
//                    while (true){
//                    if (scanner.hasNextInt()){
//                        int num = scanner.nextInt();
//                        if (num <= files.size() && num>0){
//                            magazines = openFile(files.get(num-1));
//                            break;}
//                    }
//                    else
//                        System.out.println("Повторите ввод");}
//                    break;
                default: System.out.println("Отмена действия");break;
            }
            for(Magazine m: magazines){
                writer.println(m.toString2());}
        }
        catch (IOException e) {
            System.out.println("Ошибка чтения базы данных");
        }
        if (magazines.size() != 0){
            rewriteFiles(openFile(data_1_patch));
            System.out.println("Данные о журналах обновлены");}
    }
    // ТАБЛИЦА +
    // БЛОК СХЕМА +
    // Проверил всё заново -
    private static void rewriteFiles(ArrayList<Magazine> magazines) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(data_2_patch))) {
            for (Magazine m : magazines){
                writer.println(m.getAuthor() + " " + m.getName());
            }
        } catch (Exception e) {
//            System.out.println("Ошибка перезаписи файла 2: " + e.getMessage());
        }

        try (PrintWriter writer = new PrintWriter(new FileWriter(data_3_patch))) {
            for (Magazine m: magazines)
                if ((m.getCost() > min_number) && (m.getCost() < max_number)) {
                    writer.println(m.getName() + ", " + m.getCost());
                }
        } catch (Exception e) {
//            System.out.println("Ошибка перезаписи файла 3: " + e.getMessage());
        }
        try (PrintWriter writer = new PrintWriter(new FileWriter(data_4_patch))) {
            double mid_cost = 0;
            double max_cost = (magazines.getFirst()).getCost();
            String max_name = (magazines.getFirst()).getName();
            double min_cost = (magazines.getFirst()).getCost();
            String min_name = (magazines.getFirst()).getName();
            for (Magazine m : magazines) {
                mid_cost += m.getCost();
                if (max_cost < m.getCost()) {
                    max_cost = m.getCost();
                    max_name = m.getName();
                }
                if (min_cost > m.getCost()) {
                     min_cost = m.getCost();
                    min_name = m.getName();
                }
            }
            writer.println("Самый дешевый журнал: " + min_name + " " + min_cost);
            writer.println("Самый дорогой журнал: " + max_name + " " + max_cost);
            writer.println(String.format(Locale.US, "Средняя цена журнала: %.2f", mid_cost / (double) magazines.size()));
      } catch (Exception e) {
//            System.out.println("Ошибка перезаписи файла 4: " + e.getMessage());
        }
    }
    //Открытие файла для дозаписи или вывода на экран
    // ТАБЛИЦА +
    // БЛОК СХЕМА +
    // Проверил всё заново -
    private static ArrayList<Magazine> openFile(String file){
        ArrayList<Magazine> magazines = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] line_list = line.trim().split(",");
                magazines.add(new Magazine(line_list[0], line_list[1], Double.parseDouble(line_list[2])));
            }
        } catch (Exception e) {}
        return magazines;
    }
    // ТАБЛИЦА +
    // БЛОК СХЕМА +
    // Проверил всё заново -
    private static ArrayList<String> printSmallFile(String file){
        ArrayList<String> magazines = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                line = line.replace(",", " ");
                System.out.println(line);
                magazines.add(line);
            }
        } catch (Exception e) {System.out.println("Ошибка чтения Базы данных");}
        return magazines;
    }
    // ТАБЛИЦА +
    // БЛОК СХЕМА -
    // Проверил всё заново -
    private static void printFile(ArrayList<Magazine> magazines){
        if(magazines.size() != 0){
        int mName = (magazines.getFirst().getName()).length();
        int nAuthor = (magazines.getFirst().getAuthor()).length();
        for(int i = 0; i < magazines.size(); i++){
            if((magazines.get(i).getName()).length()>mName)
                mName = (magazines.get(i).getName()).length();
            if((magazines.get(i).getAuthor()).length()>nAuthor)
                nAuthor = (magazines.get(i).getAuthor()).length();
        }
        System.out.printf("%" + mName + "s\t", "Название");
        System.out.printf("%" + nAuthor + "s\t", "Автор");
        System.out.println();

        for(int i = 0; i < magazines.size(); i++){
            System.out.print(i+1+" ");
            System.out.printf("%" + mName + "s\t", magazines.get(i).getName());
            System.out.printf("%" + nAuthor + "s\t", magazines.get(i).getAuthor());
            System.out.println(magazines.get(i).getCost());
        }
        }

    }

    // ТАБЛИЦА +
    // БЛОК СХЕМА -
    // Проверил всё заново -
    private static void deleteMagazine(int move, ArrayList<Magazine> magazines){
        ArrayList<Magazine> mag2 = new ArrayList<Magazine>();
        if(move > magazines.size()){
            System.out.println("Вы ввели номер которого не существует");

        }
        else {
        for(int i = 0; i<magazines.size(); i++){
            if(i+1 == move) {
                continue;
            }
            else{
                mag2.add(magazines.get(i));
            }
        }
        try (PrintWriter writer = new PrintWriter(new FileWriter(data_1_patch))) {

            for(Magazine m: mag2)
                writer.println(m.toString2());
            System.out.println("Данные о журналах обновлены");
        } catch (IOException e) {System.out.println("Ошибка при удалении из файла");}
            rewriteFiles(openFile(data_1_patch));
}
    }
    // ТАБЛИЦА -
    // БЛОК СХЕМА -
    // Проверил всё заново -
    private static void setFileName(String file){
        System.out.println("Текущее название: " + file);
        System.out.println("""
                Выберите оставить старое название или ввести новое
                1 — Ввести новое название
                любое другое целое число — оставить как есть""");
        Scanner scanner = new Scanner(System.in);
        while (!scanner.hasNextInt()){
            System.out.println("Повторите ввод действия");
            scanner.next();
        }
        switch (scanner.nextInt()){
            case 1:
                String newFile = "";
                System.out.println("Введите название без формата");
                while (newFile.isEmpty()){
                    newFile = scanner.nextLine();
                    if((newFile+".csv").equals(data_1_patch) || (newFile+".csv").equals(data_4_patch) ||
                            (newFile+".csv").equals(data_3_patch) || (newFile+".csv").equals(data_2_patch)||
                            (newFile+".csv").equals("config.csv")){
                        System.out.println("название файла совпадает с уже существующим");
                        newFile = "";
                    }                }
                newFile = newFile+".csv";
                File oldfile = new File(file);
                File newfile2 = new File(newFile);
                if(file.equals(data_2_patch))
                    data_2_patch = newFile;
                if(file.equals(data_3_patch))
                    data_3_patch = newFile;
                if(file.equals(data_4_patch))
                    data_4_patch = newFile;
                if (oldfile.renameTo(newfile2)) {
                    System.out.println("Файл успешно переименован: " + newfile2.getName());
                    try (PrintWriter writer = new PrintWriter(new FileWriter("config.csv"))) {
                        writer.println(data_2_patch);
                        writer.println(data_3_patch);
                        writer.println(data_4_patch);
                    } catch (IOException e) {
//                        System.out.println("Ошибка записи файла конфигурации: " + e.getMessage());
                    }
                } else {
                    System.out.println("Ошибка при переименовании файла.");
                }
                break;
            default:
                System.out.println("Вы оставили текущее название файла");
        }}

    // ТАБЛИЦА +
    // БЛОК СХЕМА +
    // Проверил всё заново -
    private static void sort(String parameters){
        ArrayList<Magazine> mag = openFile(data_1_patch);
        switch (parameters){
            case "1":System.out.println("Сортировка названия по А-Я");mag.sort(Comparator.comparing(Magazine::getName)); break;
            case "2":System.out.println("Сортировка названия по Я-А");mag.sort(Comparator.comparing(Magazine::getName).reversed());break;
            case "3":System.out.println("Сортировка имени автора по А-Я");mag.sort(Comparator.comparing(Magazine::getAuthor)); break;
            case "4":System.out.println("Сортировка имени автора по Я-А");mag.sort(Comparator.comparing(Magazine::getAuthor).reversed()); break;
            case "5":System.out.println("Сортировка по Возрастанию цены");mag.sort(Comparator.comparingDouble(Magazine::getCost)); break;
            case "6":System.out.println("Сортировка по убыванию цены");mag.sort(Comparator.comparingDouble(Magazine::getCost).reversed()); break;
            default: System.out.println("Параметры сортировки введены неверно");break;
        }
        try (PrintWriter writer = new PrintWriter(new FileWriter(data_1_patch))) {
            for(Magazine m: mag)
                writer.println(m.toString2());
            System.out.println("Сортировка прошла успешно");
            rewriteFiles(mag);
        } catch (IOException e) {//System.out.println("Ошибка перезаписи основного файла 'Список толстых журналов': " + e.getMessage());
        }
    }
}


