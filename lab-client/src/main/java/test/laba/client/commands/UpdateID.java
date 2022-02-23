package test.laba.client.commands;



import test.laba.client.console.Console;
import test.laba.client.console.ConsoleParsing;
import test.laba.client.exception.VariableException;
import test.laba.client.exception.exucuteError;
import test.laba.client.mainClasses.Root;
import test.laba.client.mainClasses.UnitOfMeasure;

import java.time.ZonedDateTime;

public class UpdateID extends AbstractCommand {
    public UpdateID(){
        super("UpdateID","обновить значение элемента коллекции, id которого равен заданному");
   }
    public void updateID(Root root, Long id, Console console, ConsoleParsing parsingInterface) throws exucuteError {
        boolean flag=true;
        String answer = null;
        Integer x = null;
        Float y = null;
        Long price = null;
        UnitOfMeasure unitOfMeasure = null;
        ZonedDateTime birthday = null;
        // запрашиваем необходимо ли изменение и изменяем
        if( console.askQuestion("Хотите изменить название продукта?")){
            console.print("Имя продукта: "+root.getProducts().get(id).getName());
            while (flag){
                answer = console.askFullQuestion("Введите измененное имя:");
                try{
                    parsingInterface.toRightName(answer,console);
                    flag=false;
                }
                catch (VariableException e){
                    console.printError("Повторите ввод");
                }
            }
            root.getProducts().get(id).setName(answer);
        }


        flag=true;
        if(console.askQuestion("Хотите изменить координаты?")){
            if(console.askQuestion("Хотите изменить координату X?")){
                console.print("Координата X: "+root.getProducts().get(id).getCoordinates().getX());
                while (flag){
                    answer= console.askFullQuestion("Введите координату х:");
                    try {
                        x= parsingInterface.toRightX(answer,console);
                        flag=false;
                    }
                    catch (VariableException e){
                        console.printError("Повторите ввод");
                    }
                }
                root.getProducts().get(id).getCoordinates().setX(x);
            }
            else {
                console.print("Координата Y: " + root.getProducts().get(id).getCoordinates().getY());
                while (flag){
                    answer= console.askFullQuestion("Введите координату y:");
                    flag=false;
                    try {
                        y= parsingInterface.toRightY(answer,console);
                    }
                    catch (VariableException e){
                        console.printError("Повторите ввод");
                    }
                }
                root.getProducts().get(id).getCoordinates().setY(y);
            }
        }

        flag=true;
        if(console.askQuestion("Хотите изменить price?")){
            console.print("Price: "+root.getProducts().get(id).getPrice());
            while (flag){
                answer= console.askFullQuestion("Введите price:");
                try {
                    price= parsingInterface.toRightPrice(answer,console);
                    flag=false;
                }
                catch (VariableException e){
                    console.printError("Повторите ввод");
                }
            }
            root.getProducts().get(id).setPrice(price);

        }

        flag=true;
        if(console.askQuestion("Хотите изменить manufactureCost?")){
            console.print("ManufactureCost: "+root.getProducts().get(id).getManufactureCost());
            while (flag){
                answer= console.askFullQuestion("Введите manufactureCost:");
                try {
                    x= parsingInterface.toRightNumber(answer,console);
                    flag=false;
                }
                catch (VariableException e){
                    console.printError("Повторите ввод");
                }
            }
            root.getProducts().get(id).setManufactureCost(x);

        }

        flag=true;
        if(console.askQuestion("Хотите изменить unitOfMeasure?")){
            console.print("UnitOfMeasure: "+root.getProducts().get(id).getUnitOfMeasure());
            while (flag){
                answer= console.askFullQuestion("""
                        Введите unitOfMeasure, возможные варианты: PCS,
                            MILLILITERS,
                            GRAMS,""");
                try {
                    unitOfMeasure= parsingInterface.toRightUnitOfMeasure(answer,console);
                    flag=false;
                }
                catch (VariableException e){
                    console.printError("Повторите ввод");
                }
            }
            root.getProducts().get(id).setUnitOfMeasure(unitOfMeasure);

        }

        flag=true;
        if(console.askQuestion("Хотите изменить owner?")){
            console.print("Owner: "+root.getProducts().get(id).getOwner());
            if(console.askQuestion("Хотите изменить имя владельца?")) {
                console.print("UnitOfMeasure: "+root.getProducts().get(id).getOwner().getName());
                while (flag){
                    answer= console.askFullQuestion("Введите имя владельца");
                    try {
                        parsingInterface.toRightName(answer,console);
                        flag=false;
                    }
                    catch (VariableException e){
                        console.printError("Повторите ввод");
                    }
                }
                root.getProducts().get(id).getOwner().setName(answer);
            }
            flag=true;
            if(console.askQuestion("Хотите изменить дату рождения владельца?")) {
                console.print("Birthday: "+root.getProducts().get(id).getOwner().getBirthday());
                while (flag){
                    answer= console.askFullQuestion("Введите новую дату рождения владельца:");
                    try {
                        birthday= parsingInterface.toRightBirthday(answer,console);
                        flag=false;
                    }
                    catch (VariableException e){
                        console.printError("Повторите ввод");
                    }
                }
                root.getProducts().get(id).getOwner().setBirthday(birthday);
            }
            flag=true;
            if(console.askQuestion("Хотите изменить рост владельца?")) {
                console.print("Height: "+root.getProducts().get(id).getOwner().getHeight());
                while (flag){
                    answer= console.askFullQuestion("Введите новый рост владельца:");
                    try {
                        x= parsingInterface.toRightHeight(answer,console);
                        flag=false;
                    }
                    catch (VariableException e){
                        console.printError("Повторите ввод");
                    }
                }
                root.getProducts().get(id).getOwner().setHeight(x);
            }
            if(console.askQuestion("Хотите изменить локацию владельца?")){
                console.print("Location: "+root.getProducts().get(id).getOwner().getLocation());
                flag=true;
                if(console.askQuestion("Хотите изменить координату х локации?")){
                    console.print("Location x: "+root.getProducts().get(id).getOwner().getLocation().getX());
                    while (flag){
                        answer= console.askFullQuestion("Введите новый координату х локации:");
                        try {
                            price= parsingInterface.toRightNumberLong(answer,console);
                            flag=false;
                        }
                        catch (VariableException e){
                            console.printError("Повторите ввод");
                        }
                    }
                    root.getProducts().get(id).getOwner().getLocation().setX(price);
                }
                flag=true;
                if(console.askQuestion("Хотите изменить координату у локации?")){
                    console.print("Location y: "+root.getProducts().get(id).getOwner().getLocation().getY());
                    while (flag){
                        answer= console.askFullQuestion("Введите новый координату y локации:");
                        try {
                            x= parsingInterface.toRightNumber(answer,console);
                            flag=false;
                        }
                        catch (VariableException e){
                            console.printError("Повторите ввод");
                        }
                    }
                    root.getProducts().get(id).getOwner().getLocation().setY(x);
                }
                if(console.askQuestion("Хотите изменить название локации?")){
                    console.print("Location: "+root.getProducts().get(id).getOwner().getLocation().getName());
                    while (flag){
                        answer= console.askFullQuestion("Введите новое название локации:");
                        try {
                            parsingInterface.toRightName(answer,console);
                            flag=false;
                        }
                        catch (VariableException e){
                            console.printError("Повторите ввод");
                        }
                    }
                    root.getProducts().get(id).getOwner().getLocation().setName(answer);
                }
            }

        }

     }

    }

