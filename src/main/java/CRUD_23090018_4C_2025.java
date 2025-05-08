import com.mongodb.client.*;
import com.mongodb.client.model.Filters;
import org.bson.Document;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;

public class CRUD_23090018_4C_2025 {
    private static MongoCollection<Document> collection;

    public static void main(String[] args) {
        // ================= KONEKSI KE MONGODB =================
        MongoClient mongoClient = MongoClients.create("mongodb://localhost:27017");
        MongoDatabase database = mongoClient.getDatabase("uts_23090018_4C_2025");
        collection = database.getCollection("coll_23090018_4C_2025");

        // ================= DESAIN UI SWING ====================
        JFrame frame = new JFrame("CRUD MongoDB - UTS 2025");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 500);
        frame.setLayout(new BorderLayout());

        JTextField tfNama = new JTextField(15);
        JTextField tfKey = new JTextField(15);
        JTextField tfValue = new JTextField(15);
        JTextArea output = new JTextArea(15, 40);
        output.setEditable(false);

        JPanel inputPanel = new JPanel();
        inputPanel.add(new JLabel("Nama:"));
        inputPanel.add(tfNama);
        inputPanel.add(new JLabel("Key:"));
        inputPanel.add(tfKey);
        inputPanel.add(new JLabel("Value:"));
        inputPanel.add(tfValue);

        JPanel buttonPanel = new JPanel();
        JButton btnCreate = new JButton("Create");
        JButton btnRead = new JButton("Read");
        JButton btnUpdate = new JButton("Update");
        JButton btnDelete = new JButton("Delete");
        JButton btnSearch = new JButton("Search");

        buttonPanel.add(btnCreate);
        buttonPanel.add(btnRead);
        buttonPanel.add(btnUpdate);
        buttonPanel.add(btnDelete);
        buttonPanel.add(btnSearch);

        frame.add(inputPanel, BorderLayout.NORTH);
        frame.add(buttonPanel, BorderLayout.CENTER);
        frame.add(new JScrollPane(output), BorderLayout.SOUTH);

        // ================= AKSI TOMBOL ========================
        btnCreate.addActionListener((ActionEvent e) -> {
            // Tambah dokumen baru
            Document doc = new Document("nama", tfNama.getText())
                    .append(tfKey.getText(), tfValue.getText());
            collection.insertOne(doc);
            output.setText("✅ Data berhasil ditambahkan.");
        });

        btnRead.addActionListener((ActionEvent e) -> {
            // Tampilkan semua data
            List<String> data = new ArrayList<>();
            for (Document doc : collection.find()) {
                data.add(doc.toJson());
            }
            output.setText(String.join("\n", data));
        });

        btnUpdate.addActionListener((ActionEvent e) -> {
            // Update data berdasarkan nama
            Document newData = new Document(tfKey.getText(), tfValue.getText());
            collection.updateOne(Filters.eq("nama", tfNama.getText()), new Document("$set", newData));
            output.setText("🔄 Data berhasil diperbarui.");
        });

        btnDelete.addActionListener((ActionEvent e) -> {
            // Hapus data berdasarkan nama
            collection.deleteOne(Filters.eq("nama", tfNama.getText()));
            output.setText("🗑️ Data berhasil dihapus.");
        });

        btnSearch.addActionListener((ActionEvent e) -> {
            // Cari data berdasarkan nama
            Document result = collection.find(Filters.eq("nama", tfNama.getText())).first();
            if (result != null) {
                output.setText("🔍 Ditemukan:\n" + result.toJson());
            } else {
                output.setText("❌ Data tidak ditemukan.");
            }
        });

        frame.setVisible(true);
    }
}
