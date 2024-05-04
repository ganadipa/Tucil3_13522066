    package GUI.panel;

    import java.awt.BorderLayout;
    import java.awt.FlowLayout;
    import java.io.File;
    import java.io.FileWriter;

    import javax.swing.JFileChooser;
    import javax.swing.JLabel;
    import javax.swing.JPanel;

    import GUI.component.NormalButton;
    import GUI.theme.Colors;
    import GUI.theme.Fonts;

    public class Menu extends JPanel {
        //import variable
        protected JPanel fileChooserPanel;
        private JFileChooser fileChooser = new JFileChooser();
        private NormalButton fileChooserButton = new NormalButton("Open");
        private JLabel fileChooserLabel = new JLabel("Import dari file: ");
        private JLabel choosenFileNameLabel = new JLabel("");
        private JLabel choosenFileLabel = new JLabel("");

        private JLabel errorMessage = new JLabel("");
        protected JPanel errorPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        
        // export result
        public JPanel exportPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        NormalButton exportButton = new NormalButton("Export hasil");
        String stringResult = "";

        public Menu () {
            fileChooserLabel.setForeground(Colors.green400);
            fileChooserLabel.setBackground(Colors.green400);
            choosenFileNameLabel.setForeground(Colors.green400);
            choosenFileNameLabel.setBackground(Colors.green400);
            choosenFileLabel.setForeground(Colors.green400);
            choosenFileLabel.setBackground(Colors.green400);


            fileChooserPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
            fileChooserPanel.setForeground(Colors.green400);
            fileChooserPanel.setBackground(Colors.green400);
            fileChooserButton.addActionListener(e -> {
                fileChooser.showOpenDialog(this);
                fileChooser.setDialogTitle("Pilih file");
                choosenFileNameLabel.setText(fileChooser.getSelectedFile().getName());
                choosenFileNameLabel.repaint();
                choosenFileNameLabel.revalidate();
                choosenFileLabel.setText("File terpilih: ");
                choosenFileLabel.repaint();
                choosenFileLabel.revalidate();
                onFileChoosen(fileChooser.getSelectedFile());
            });

            fileChooserPanel.add(fileChooserLabel);
            fileChooserPanel.add(fileChooserButton);
            fileChooserPanel.add(choosenFileLabel);
            fileChooserPanel.add(choosenFileNameLabel);

            errorMessage.setForeground(Colors.green400);
            errorMessage.setBackground(Colors.green400);
            errorMessage.setLayout(new FlowLayout(FlowLayout.LEFT));
            errorPanel.setForeground(Colors.green400);
            errorPanel.setBackground(Colors.green400);
            errorPanel.add(errorMessage);


            // Export result
            exportPanel.setForeground(Colors.green400);
            exportPanel.setBackground(Colors.green400);
            exportPanel.add(exportButton);
            exportButton.addActionListener(e -> {   
                fileChooser.setDialogTitle("Export hasil");
                fileChooser.setSelectedFile(new File("output.txt"));
                fileChooser.showSaveDialog(this);
                try {
                    FileWriter myWriter = new FileWriter(fileChooser.getSelectedFile());
                    myWriter.write(stringResult);
                    myWriter.close();
                } catch (Exception ex) {
                    onException(ex);
                }
                
            });
        }

        /**
        * Menggambar text ke layar.
        * @param  text text yang akan ditampilkan
        */
        protected void addText(String text) {
            JPanel panel = new JPanel();
            panel.setBackground(Colors.green50);
            panel.setLayout(new FlowLayout(FlowLayout.LEFT));

            JLabel label = new JLabel(text);
            label.setForeground(Colors.green50);
            label.setAlignmentX(LEFT_ALIGNMENT);
            panel.add(label);
            
            add(panel, BorderLayout.PAGE_START);
        }
        /**
        * Menggambar text ke layar.
        * @param  text text yang akan ditampilkan
        */
        protected void addBigText(String text) {
            JPanel panel = new JPanel();
            panel.setBackground(Colors.green50);
            panel.setLayout(new FlowLayout(FlowLayout.LEFT));

            JLabel label = new JLabel(text);
            label.setForeground(Colors.green50);
            label.setAlignmentX(LEFT_ALIGNMENT);
            label.setFont(Fonts.font20);
            panel.add(label);
            
            add(panel, BorderLayout.PAGE_START);
        }
        /**
        * Menggambar matrix ke layar.
        * @param matrix matrix yang akan ditampilkan
        */
        // protected void addMatrix(Matrix matrix) {
        //     JPanel panel = new JPanel(new GridLayout(matrix.row, matrix.col));
        //     panel.setBackground(Colors.green50);

        //     for (int i = 0; i < matrix.row; i++) {
        //         for (int j = 0; j < matrix.col; j++) {
        //             JLabel label = new JLabel(String.format("%.3f", matrix.matrix[i][j]));
        //             label.setForeground(Colors.green50);
        //             label.setAlignmentX(LEFT_ALIGNMENT);
        //             panel.add(label);
        //         }
        //     }
        //     add(panel);
        // }

        /**
        * Inisiasi variabel setelah memilih file.
        * @param  text text yang akan ditampilkan
        */
        protected void onFileChoosen(File file) {

        }

        protected void onException(Exception e) {
            setErrorMessage(e.getMessage());
        }
        protected void onError(Error e) {
            setErrorMessage(e.getMessage());
        }
        void setErrorMessage(String message){
            if(message.equals("empty String"))
                errorMessage.setText("Error: masih ada elemen yang kosong");
            else 
                errorMessage.setText("Error: " + message);
            
            errorMessage.repaint();
            errorMessage.revalidate();
            setResult("");
        }
        protected void resetError() {
            errorMessage.setText("");
            errorMessage.repaint();
            errorMessage.revalidate();
        }

        protected void setResult(String text) {
            stringResult = text;
        }

        protected String stringToHtml(String s) {
            return "<html>" + s.replaceAll("\n", "<br/>") + "</html>";
        }
    }