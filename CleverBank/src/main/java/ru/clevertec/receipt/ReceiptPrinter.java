package ru.clevertec.receipt;


import com.aspose.pdf.*;
import lombok.Data;
import lombok.NonNull;
import ru.clevertec.entity.Transaction;

import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Random;
@Data
public class ReceiptPrinter {
    public static void printReceipt(@NonNull Transaction transaction) {
        Document doc = new Document();
        Page page = doc.getPages().add();
        Table table = new Table();
        table.setBorder(new BorderInfo(BorderSide.All, .5f, Color.getLightGray()));
        table.setDefaultCellBorder(new BorderInfo(BorderSide.All, .5f, Color.getLightGray()));
        table.setDefaultCellBorder(new BorderInfo(BorderSide.None, 0f));
        Row row = table.getRows().add();
        row.getCells().add("Bank receipt").setColSpan(2);
        Row row1 = table.getRows().add();
        row1.getCells().add("Receipt:");
        row1.getCells().add(String.valueOf(new Random().nextInt(90000) + 10000));
        Row row2 = table.getRows().add();
        row2.getCells().add(transaction.getDate().format(DateTimeFormatter.ofPattern("dd.MM.yyyy").withLocale(Locale.forLanguageTag("ru"))));
        row2.getCells().add(transaction.getDate().format(DateTimeFormatter.ofPattern("HH:mm:ss").withLocale(Locale.forLanguageTag("ru"))));
        if (transaction.getFromAccount() == null) {
            Row row3 = table.getRows().add();
            row3.getCells().add("Type transaction:");
            row3.getCells().add(String.valueOf(transaction.getTypeTransaction()));
            Row row4 = table.getRows().add();
            row4.getCells().add("Client's bank:");
            row4.getCells().add(transaction.getToAccount().getBank().getTitle());
            Row row5 = table.getRows().add();
            row5.getCells().add("Client's account:");
            row5.getCells().add(transaction.getToAccount().getNumber());
            Row row6 = table.getRows().add();
            row6.getCells().add("Amount:");
            row6.getCells().add(transaction.getAmount() + " " + transaction.getToAccount().getCurrency());
        } else if (transaction.getToAccount() == null) {
            Row row3 = table.getRows().add();
            row3.getCells().add("Type transaction:");
            row3.getCells().add(String.valueOf(transaction.getTypeTransaction()));
            Row row4 = table.getRows().add();
            row4.getCells().add("Client's bank:");
            row4.getCells().add(transaction.getFromAccount().getBank().getTitle());
            Row row5 = table.getRows().add();
            row5.getCells().add("Client's account:");
            row5.getCells().add(transaction.getFromAccount().getNumber());
            Row row6 = table.getRows().add();
            row6.getCells().add("Amount:");
            row6.getCells().add(transaction.getAmount() + " " + transaction.getFromAccount().getCurrency());
        } else {
            Row row3 = table.getRows().add();
            row3.getCells().add("Type transaction:");
            row3.getCells().add(String.valueOf(transaction.getTypeTransaction()));
            Row row4 = table.getRows().add();
            row4.getCells().add("Sender's Bank:");
            row4.getCells().add(transaction.getFromAccount().getBank().getTitle());
            Row row5 = table.getRows().add();
            row5.getCells().add("Recipient's bank:");
            row5.getCells().add(transaction.getToAccount().getBank().getTitle());
            Row row6 = table.getRows().add();
            row6.getCells().add("Sender's account:");
            row6.getCells().add(transaction.getFromAccount().getNumber());
            Row row7 = table.getRows().add();
            row7.getCells().add("Recipient's account:");
            row7.getCells().add(transaction.getToAccount().getNumber());
            Row row8 = table.getRows().add();
            row8.getCells().add("Amount:");
            row8.getCells().add(transaction.getAmount() + " " + transaction.getFromAccount().getCurrency());
        }
        doc.getPages().get_Item(1).getParagraphs().add(table);
        doc.save("check/receipt" + (new Random().nextInt(90000) + 10000) +".pdf");
    }
}
