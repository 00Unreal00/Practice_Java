import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.File;
import java.util.*;
import java.util.regex.Pattern;
//неактуально
public class Archive {
    public static String data_1_patch;
    public static String data_2_patch;
    public static String data_3_patch;
    public static String data_4_patch;
    public static double min_number;
    public static double max_number;
    public static void main(String[] args){
    Scanner scanner = new Scanner(System.in);
        System.out.println("Введите имя 'Списка толстых журналов'");
        data_1_patch = scanner.nextLine();
    while (true){
        try(BufferedReader reader = new BufferedReader(new FileReader("config.csv"))){
            data_2_patch = reader.readLine();
            data_3_patch = reader.readLine();
            data_4_patch = reader.readLine();
        }
        catch (IOException e) {
            System.out.println("Ошибка чтения файла сохранения: " + e.getMessage());
        }
        System.out.println("""
                1 — Переименовать файлы
                2 — Перезаписать исходные данные файла
                3 — Дозаписать данные в файл
                4 — Вывести данные
                5 — Отсортировать файл
                6 - Ввести минимальное и максимальное значение""");
        while (!scanner.hasNextInt()){
            System.out.println("Повторите ввод действия");
            scanner.next();
        }
        int move = scanner.nextInt();
        switch (move){
            case 1:
                System.out.println("Введите имя файла который хотите переименовать:\n" +
                        data_2_patch + " "+ data_3_patch +  " \n"+ data_4_patch);
                String old_name = "";
                while(old_name.isEmpty()){
                    old_name = scanner.nextLine();
                    if (!old_name.equals(data_2_patch) &
                            !old_name.equals(data_3_patch) & !old_name.equals(data_4_patch)) {
                        System.out.println("Вы ввели несуществующее имя файла");
                        old_name = "";
                    }
                }
                System.out.println("введите новое название файла без формата");
                String new_name = "";
                while(new_name.isEmpty()){
                    new_name = scanner.nextLine();
                }
                change_name(old_name, new_name); break;
            case 2:
                System.out.println("1 – перезаписать список из консоли\n 2 – перезаписать список из другого файла");
                while (!scanner.hasNextInt()){
                    System.out.println("Повторите ввод");
                    scanner.next();}
                write_file(scanner.nextInt());
                rewrite_f2(open_file(data_1_patch));
                rewrite_f3(open_file(data_1_patch));
                rewrite_f4(open_file(data_1_patch));
                break;
            case 3:
                System.out.println("1 – добавить в список из консоли\n 2 – загрузить в список из файла");
                while (!scanner.hasNextInt()){
                    System.out.println("Повторите ввод");
                    scanner.next();}
                add_file(data_1_patch, scanner.nextInt());
                rewrite_f2(open_file(data_1_patch));
                rewrite_f3(open_file(data_1_patch));
                rewrite_f4(open_file(data_1_patch));break;
            case 4:
                ArrayList<Magazine> mag = open_file(data_1_patch);
                for(int i = 0; i < mag.size(); i++){
                    System.out.println(i+1+" "+mag.get(i).toString());}break;
            case 5: System.out.println("""
                    формат ввода: 8w - w8 / 8s - s8; возрастание/убывание;
                    1 — Отсортировать по названию
                    2 — Отсортировать по автору
                    3 — Отсортировать по цене""");
                String pr;
                while (true){pr = scanner.nextLine();if (Pattern.matches("[ws1234]+", pr)) break;}
                sort(pr); break;
            case 6:
                System.out.println("Введите минимальную цену");
                while (!scanner.hasNextDouble()){
                    System.out.println("Введено некоректное значение");
                    System.out.println("Введите минимальную цену");
                    scanner.next();
                }
                min_number = scanner.nextDouble();
                System.out.println("Введите максимальную цену");
                while (!scanner.hasNextDouble()){
                    System.out.println("Введено некоректное значение");
                    System.out.println("Введите максимальную цену");
                    scanner.next();
                }
                max_number = scanner.nextDouble();
                rewrite_f3(open_file(data_1_patch));
                break;
        }
    }}
    public static  void change_name(String file1, String file2){
        File oldfile = new File(file1);
        File newfile = new File(file2+".csv");
        if(file1.equals(data_1_patch))
            data_1_patch = file2+".csv";
        if(file1.equals(data_2_patch))
            data_2_patch = file2+".csv";
        if(file1.equals(data_3_patch))
            data_3_patch = file2+".csv";
        if(file1.equals(data_4_patch))
            data_4_patch = file2+".csv";
        if (oldfile.renameTo(newfile)) {
            System.out.println("Файл успешно переименован: " + newfile.getName());
            try (PrintWriter writer = new PrintWriter(new FileWriter("config.csv"))) {
                writer.println(data_1_patch);
                writer.println(data_2_patch);
                writer.println(data_3_patch);
                writer.println(data_4_patch);
            } catch (IOException e) {
                System.out.println("Ошибка записи файла конфигурации: " + e.getMessage());
            }
        } else {
            System.out.println("Ошибка при переименовании файла.");}
    }

    // Полная перезапись файлов 1-4
    public static void write_file(int move){
        try (PrintWriter writer = new PrintWriter(new FileWriter(data_1_patch))) {
            Scanner s = new Scanner(System.in);
            ArrayList<Magazine> mag = new ArrayList<Magazine>();
            switch (move){
                case 1:
                    System.out.println("Введите данные по такому шаблону\nНазвание журнала,Имя автора,Цена");
                    String line;
                    System.out.println("Для подтверждения ввода дважды нажмите Enter");
                    while(true){
                        line = s.nextLine();
                        if (line == "")
                            break;
                        String[] line2 = line.trim().split(",");
                        if (line2.length == 3){
                            try {
                                mag.add(new Magazine(line2[0].trim(), line2[1].trim(), Double.parseDouble(line2[2].trim())));
                                writer.println(line);
                            }
                            catch (Exception e){
                                System.out.println("повторите ввод цены");
                            }
                        }
                        else
                            System.out.println("Повторите ввод по шаблону");
                    }
                    break;
                case 2:
                    System.out.println("Введите 'имя.формат'");
                     mag = open_file(s.nextLine());
                     System.out.println(mag);
                    for(Magazine m: mag)
                        writer.println(m.toString2());
                    break;
            }

        } catch (IOException e) {
            System.out.println("Ошибка перезаписи файла: " + e.getMessage());
        }
    }
    public static void rewrite_f2(ArrayList<Magazine> mag){
        try (PrintWriter writer = new PrintWriter(new FileWriter(data_2_patch))){
            for(Magazine m: mag)
                writer.println(m.get_author() +" "+ m.get_name());}
        catch (IOException e) {
            System.out.println("Ошибка перезаписи файла 2: " + e.getMessage());
        }}
    public static void rewrite_f3(ArrayList<Magazine> mag){
        try (PrintWriter writer = new PrintWriter(new FileWriter(data_3_patch))){
            for(Magazine m: mag)
                if((m.get_cost() > min_number) && (m.get_cost() < max_number)){
                    writer.println(m.get_name() +" , "+ m.get_cost());}}
        catch (IOException e) {
            System.out.println("Ошибка перезаписи файла 3: " + e.getMessage());
        }}
    public static void rewrite_f4(ArrayList<Magazine> mag){
        try (PrintWriter writer = new PrintWriter(new FileWriter(data_4_patch))){
            double mid_cost = 0;
            double max_cost = (mag.getFirst()).get_cost();
            System.out.println(max_cost);
            String max_name = (mag.getFirst()).get_name();
            double min_cost = (mag.getFirst()).get_cost();
            String min_name = (mag.getFirst()).get_name();
            for(Magazine m: mag) {
                mid_cost += m.get_cost();
                if(max_cost < m.get_cost()){
                    max_cost = m.get_cost();
                    max_name = m.get_name();
                }
                if(min_cost > m.get_cost()){
                    min_cost = m.get_cost();
                    min_name = m.get_name();
                }
            }
            writer.println("Самый дешевый журнал: "+min_name+" "+min_cost);
            writer.println("Самый дорогой журнал: "+max_name+" "+max_cost);
            writer.println("Средняя цена журнала: "+mid_cost/mag.size());
        }
        catch (IOException e) {
            System.out.println("Ошибка перезаписи файла 4: " + e.getMessage());
        }}

    //Доазпись в файл
    public static void add_file(String file, int move){
        try(PrintWriter writer = new PrintWriter(new FileWriter(file, true))){
            Scanner s = new Scanner(System.in);
            switch (move){
                case 1:
                    String line;
                    ArrayList<Magazine> magazines = open_file(data_1_patch);
                    System.out.println("Введите данные по такому шаблону " +
                            "Название журнала, Имя автора, Цена");
                    System.out.println("Для подтверждения ввода дважды нажмите Enter");
                    while(true){
                        line = s.nextLine();
                        if (line.isEmpty())
                            break;
                        String[] line_list = line.trim().split(",");
                        if (line_list.length == 3){
                            try {
                                magazines.add(new Magazine(line_list[0].trim(), line_list[1].trim(), Double.parseDouble(line_list[2].trim())));
                                writer.println(line);
                            }
                            catch (Exception e){
                                System.out.println("повторите ввод числа");
                            }}}break;
                case 2:
                    System.out.println("Введите 'имя.формат'");
                    ArrayList<Magazine> mag_add = open_file(s.nextLine());
                    for(Magazine m: mag_add){
                        writer.println(m.toString2());
                    }
                    break;
            }}
        catch (IOException e) {
            System.out.println("Ошибка чтения файла: " + e.getMessage());
        }
    }


    //Открытие файла для дозаписи или вывода на экран
    public static ArrayList<Magazine> open_file(String file){
        ArrayList<Magazine> magazines = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] line_list = line.trim().split(",");
                System.out.println(Arrays.toString(line_list));
                magazines.add(new Magazine(line_list[0], line_list[1], Double.parseDouble(line_list[2])));
            }
        } catch (IOException e) {
            System.out.println("Ошибка чтения БД при открытии" + e.getMessage());
        }
        return magazines;
    }


    public static void sort(String parameters){
        ArrayList<Magazine> mag = open_file(data_1_patch);
        boolean rev = false;
        switch (parameters){
            case "1w","w1":System.out.println("Сортировка номера по возрастанию"); break;
            case "2w","w2":System.out.println("Сортировка названия по А-Я");mag.sort(Comparator.comparing(Magazine::get_name)); break;
            case "3w","w3":System.out.println("Сортировка имени автора по А-Я");mag.sort(Comparator.comparing(Magazine::get_author)); break;
            case "4w","w4":System.out.println("Сортировка по убыванию цены");mag.sort(Comparator.comparingDouble(Magazine::get_cost).reversed()); break;
            case "1s","s1":System.out.println("Сортировка номера по убыванию");rev = true;break;
            case "2s","s2":System.out.println("Сортировка названия по Я-А");mag.sort(Comparator.comparing(Magazine::get_name).reversed()); break;
            case "3s","s3":System.out.println("Сортировка имени автора по Я-А");mag.sort(Comparator.comparing(Magazine::get_author).reversed()); break;
            case "4s","s4":System.out.println("Сортировка по Возрастанию цены");mag.sort(Comparator.comparingDouble(Magazine::get_cost)); break;
        }
//        if(!rev){
//        for(int i = 0; i < mag.size(); i++){
//            System.out.println(i+1+" "+mag.get(i).toString());
//        }}
//        else {
//            for(int i = mag.size()-1; i >= 0; i--){
//                System.out.println(i+1+" "+mag.get(i).toString());
//            }}
        try (PrintWriter writer = new PrintWriter(new FileWriter(data_1_patch))) {
            for(Magazine m: mag)
                writer.println(m.toString2());
            rewrite_f2(mag);
            rewrite_f3(mag);
            rewrite_f4(mag);
        } catch (IOException e) {
            System.out.println("Ошибка перезаписи файла: " + e.getMessage());
        }
    }
}



