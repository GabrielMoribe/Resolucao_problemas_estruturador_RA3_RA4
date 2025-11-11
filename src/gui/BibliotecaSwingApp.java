//Testar se a aplicação abre
//cd "/Users/gabriel/Documents/4º Semestre/Estrutura de Dados/Biblioteca de jogos/src"
//java gui.BibliotecaSwingApp


package gui;

import model.Biblioteca;
import model.Jogo;
import algoritmos.BubbleSort;
import algoritmos.InsertionSort;
import algoritmos.QuickSort;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;


public class BibliotecaSwingApp extends JFrame {

    private Biblioteca biblioteca;
    private JTable tabelaJogos;
    private DefaultTableModel modeloTabela;
    private JTextField campoId, campoTitulo, campoGenero, campoAno;
    private JLabel statusLabel;

    public BibliotecaSwingApp() {
        System.out.println("Iniciando BibliotecaSwingApp...");
        biblioteca = new Biblioteca();

        // Adicionar alguns jogos de exemplo
        // Adicionando jogos à biblioteca
//        biblioteca.adicionarJogo(new Jogo(1 , "Jogo 1", "Ação", 2020));
//        biblioteca.adicionarJogo(new Jogo(2 , "Jogo 2", "Aventura", 2019));
//        biblioteca.adicionarJogo(new Jogo(3 , "Jogo 3", "RPG", 2021));
//        biblioteca.adicionarJogo(new Jogo(4 , "Jogo 4", "Aventura", 2018));
//        biblioteca.adicionarJogo(new Jogo(5 , "Jogo 5", "Ação", 2022));
//        biblioteca.adicionarJogo(new Jogo(6 , "Jogo 6", "RPG", 2023));
//        biblioteca.adicionarJogo(new Jogo(7 , "Jogo 7", "Aventura", 2024));
//        biblioteca.adicionarJogo(new Jogo(8 , "Jogo 8", "RPG", 2025));
//        biblioteca.adicionarJogo(new Jogo(9 , "Jogo 9", "Aventura", 2026));
//        biblioteca.adicionarJogo(new Jogo(10, "Jogo 10", "RPG", 2027));



        System.out.println("Biblioteca criada com " + biblioteca.getTamanho() + " jogos");

        inicializarInterface();
        System.out.println("Interface inicializada");

        gerarDadosDeTeste(1000); // Para 1000 jogos

        atualizarTabela();
        System.out.println("Tabela atualizada - Aplicação pronta!");
    }

    private void gerarDadosDeTeste(int quantidade) {
        // Limpar biblioteca atual
        biblioteca = new Biblioteca();

        String[] titulos = {
                "Call of Duty", "FIFA", "Grand Theft Auto", "The Legend of Zelda", "Super Mario",
                "Minecraft", "Fortnite", "Assassin's Creed", "World of Warcraft", "Counter-Strike",
                "League of Legends", "Overwatch", "Red Dead Redemption", "The Witcher", "Cyberpunk",
                "Final Fantasy", "God of War", "Halo", "Doom", "Battlefield", "Elder Scrolls",
                "Fallout", "Resident Evil", "Silent Hill", "Metal Gear Solid", "Dark Souls",
                "Bloodborne", "Sekiro", "Elden Ring", "Pokemon", "Animal Crossing", "Splatoon"
        };

        String[] generos = {
                "Ação", "Aventura", "RPG", "Estratégia", "Simulação", "Esporte", "Corrida",
                "Puzzle", "Plataforma", "Luta", "Terror", "Sobrevivência", "MMORPG", "FPS"
        };

        java.util.Random random = new java.util.Random();

        for (int i = 1; i <= quantidade; i++) {
            String titulo = titulos[random.nextInt(titulos.length)] + " " + i;
            String genero = generos[random.nextInt(generos.length)];
            int ano = 1990 + random.nextInt(35); // Anos de 1990 a 2024

            biblioteca.adicionarJogo(new Jogo(i, titulo, genero, ano));
        }

        atualizarTabela();
        mostrarStatus("Gerados " + quantidade + " jogos de teste", false);
    }


    private void inicializarInterface() {
        setTitle("Biblioteca de Jogos - Sistema de Gerenciamento");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Painel superior - formulário
        JPanel painelFormulario = criarPainelFormulario();
        add(painelFormulario, BorderLayout.NORTH);

        // Painel central - tabela
        JPanel painelTabela = criarPainelTabela();
        add(painelTabela, BorderLayout.CENTER);

        // Painel inferior - status e ordenação
        JPanel painelInferior = criarPainelInferior();
        add(painelInferior, BorderLayout.SOUTH);

        setSize(1000, 700);
        setLocationRelativeTo(null);

        // Garantir que a aplicação termine ao fechar
        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent e) {
                System.out.println("Aplicação sendo fechada...");
                System.exit(0);
            }
        });
    }

    private JPanel criarPainelFormulario() {
        JPanel painel = new JPanel(new GridBagLayout());
        painel.setBorder(BorderFactory.createTitledBorder("Gerenciar Jogos"));
        painel.setBackground(new Color(240, 240, 240));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        // Campos de entrada
        gbc.gridx = 0; gbc.gridy = 0;
        painel.add(new JLabel("ID:"), gbc);
        gbc.gridx = 1;
        campoId = new JTextField(10);
        painel.add(campoId, gbc);

        gbc.gridx = 2; gbc.gridy = 0;
        painel.add(new JLabel("Título:"), gbc);
        gbc.gridx = 3;
        campoTitulo = new JTextField(15);
        painel.add(campoTitulo, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        painel.add(new JLabel("Gênero:"), gbc);
        gbc.gridx = 1;
        campoGenero = new JTextField(10);
        painel.add(campoGenero, gbc);

        gbc.gridx = 2; gbc.gridy = 1;
        painel.add(new JLabel("Ano:"), gbc);
        gbc.gridx = 3;
        campoAno = new JTextField(10);
        painel.add(campoAno, gbc);

        // Botões
        JPanel painelBotoes = new JPanel(new FlowLayout());

        JButton btnAdicionar = new JButton("Adicionar Jogo");
        aplicarCorComContraste(btnAdicionar, new Color(76, 175, 80));
        btnAdicionar.addActionListener(e -> adicionarJogo());

        JButton btnRemover = new JButton("Remover Jogo");
        aplicarCorComContraste(btnRemover, new Color(244, 67, 54));
        btnRemover.addActionListener(e -> removerJogo());

        JButton btnBuscar = new JButton("Buscar por ID");
        aplicarCorComContraste(btnBuscar, new Color(33, 150, 243));
        btnBuscar.addActionListener(e -> buscarJogo());

        JButton btnLimpar = new JButton("Limpar Campos");
        aplicarCorComContraste(btnLimpar, new Color(158, 158, 158));
        btnLimpar.addActionListener(e -> limparCampos());

        painelBotoes.add(btnAdicionar);
        painelBotoes.add(btnRemover);
        painelBotoes.add(btnBuscar);
        painelBotoes.add(btnLimpar);

        gbc.gridx = 0; gbc.gridy = 2;
        gbc.gridwidth = 4;
        painel.add(painelBotoes, gbc);

        return painel;
    }

    private JPanel criarPainelTabela() {
        JPanel painel = new JPanel(new BorderLayout());
        painel.setBorder(BorderFactory.createTitledBorder("Lista de Jogos"));

        // Criar modelo da tabela
        String[] colunas = {"ID", "Título", "Gênero", "Ano"};
        modeloTabela = new DefaultTableModel(colunas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Tabela não editável
            }
        };

        tabelaJogos = new JTable(modeloTabela);
        tabelaJogos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tabelaJogos.setRowHeight(25);

        // Configurar larguras das colunas
        tabelaJogos.getColumnModel().getColumn(0).setPreferredWidth(50);
        tabelaJogos.getColumnModel().getColumn(1).setPreferredWidth(300);
        tabelaJogos.getColumnModel().getColumn(2).setPreferredWidth(150);
        tabelaJogos.getColumnModel().getColumn(3).setPreferredWidth(100);

        JScrollPane scrollPane = new JScrollPane(tabelaJogos);
        painel.add(scrollPane, BorderLayout.CENTER);

        return painel;
    }

    private JPanel criarPainelInferior() {
        JPanel painel = new JPanel(new BorderLayout());

        // Painel de ordenação
        JPanel painelOrdenacao = new JPanel(new FlowLayout());
        painelOrdenacao.setBorder(BorderFactory.createTitledBorder("Ordenação"));

        painelOrdenacao.add(new JLabel("Algoritmo:"));
        JComboBox<String> comboAlgoritmo = new JComboBox<>(new String[]{"QuickSort", "BubbleSort", "InsertionSort"});
        painelOrdenacao.add(comboAlgoritmo);

        painelOrdenacao.add(new JLabel("Critério:"));
        JComboBox<String> comboCriterio = new JComboBox<>(new String[]{"titulo", "genero", "ano", "id"});
        painelOrdenacao.add(comboCriterio);

        JButton btnOrdenar = new JButton("Ordenar");
        aplicarCorComContraste(btnOrdenar, new Color(255, 152, 0));
        btnOrdenar.addActionListener(e -> {
            String algoritmo = (String) comboAlgoritmo.getSelectedItem();
            String criterio = (String) comboCriterio.getSelectedItem();
            ordenarJogos(algoritmo, criterio);
        });
        painelOrdenacao.add(btnOrdenar);

        JButton btnRecarregar = new JButton("Recarregar Original");
        aplicarCorComContraste(btnRecarregar, new Color(96, 125, 139));
        btnRecarregar.addActionListener(e -> atualizarTabela());
        painelOrdenacao.add(btnRecarregar);

        // Status label
        statusLabel = new JLabel("Sistema iniciado - " + biblioteca.getTamanho() + " jogos carregados");
        statusLabel.setForeground(new Color(0, 128, 0));
        statusLabel.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));

        painel.add(painelOrdenacao, BorderLayout.CENTER);
        painel.add(statusLabel, BorderLayout.SOUTH);

        return painel;
    }

    private void adicionarJogo() {
        try {
            int id = Integer.parseInt(campoId.getText().trim());
            String titulo = campoTitulo.getText().trim();
            String genero = campoGenero.getText().trim();
            int ano = Integer.parseInt(campoAno.getText().trim());

            if (titulo.isEmpty() || genero.isEmpty()) {
                mostrarStatus("Erro: Título e gênero não podem estar vazios!", true);
                return;
            }

            if (biblioteca.buscarJogo(id) != null) {
                mostrarStatus("Erro: Já existe um jogo com ID " + id, true);
                return;
            }

            Jogo novoJogo = new Jogo(id, titulo, genero, ano);
            biblioteca.adicionarJogo(novoJogo);
            atualizarTabela();
            limparCampos();
            mostrarStatus("Jogo '" + titulo + "' adicionado com sucesso!", false);

        } catch (NumberFormatException e) {
            mostrarStatus("Erro: ID e Ano devem ser números válidos!", true);
        }
    }

    private void removerJogo() {
        try {
            int id = Integer.parseInt(campoId.getText().trim());
            Jogo jogo = biblioteca.buscarJogo(id);

            if (jogo != null) {
                biblioteca.removerJogo(id);
                atualizarTabela();
                limparCampos();
                mostrarStatus("Jogo '" + jogo.getTitulo() + "' removido com sucesso!", false);
            } else {
                mostrarStatus("Erro: Jogo com ID " + id + " não encontrado!", true);
            }

        } catch (NumberFormatException e) {
            mostrarStatus("Erro: Digite um ID válido para remover!", true);
        }
    }

    private void buscarJogo() {
        try {
            int id = Integer.parseInt(campoId.getText().trim());
            Jogo jogo = biblioteca.buscarJogo(id);

            if (jogo != null) {
                campoTitulo.setText(jogo.getTitulo());
                campoGenero.setText(jogo.getGenero());
                campoAno.setText(String.valueOf(jogo.getAnoLancamento()));
                mostrarStatus("Jogo encontrado: " + jogo.getTitulo(), false);

                // Destacar na tabela
                for (int i = 0; i < modeloTabela.getRowCount(); i++) {
                    Integer tableId = (Integer) modeloTabela.getValueAt(i, 0);
                    if (tableId != null && tableId.intValue() == id) {
                        tabelaJogos.setRowSelectionInterval(i, i);
                        tabelaJogos.scrollRectToVisible(tabelaJogos.getCellRect(i, 0, true));
                        break;
                    }
                }
            } else {
                mostrarStatus("Jogo com ID " + id + " não encontrado!", true);
            }

        } catch (NumberFormatException e) {
            mostrarStatus("Erro: Digite um ID válido para buscar!", true);
        }
    }

    private void ordenarJogos(String algoritmo, String criterio) {
        Jogo[] jogos = biblioteca.exportarParaVetor();

        if (jogos.length == 0) {
            mostrarStatus("Nenhum jogo para ordenar!", true);
            return;
        }

        long tempoInicio = System.currentTimeMillis();

        switch (algoritmo) {
            case "QuickSort":
                QuickSort qs = new QuickSort();
                qs.quickSort(jogos, 0, jogos.length - 1, criterio);
                break;
            case "BubbleSort":
                BubbleSort bs = new BubbleSort();
                bs.bubbleSort(jogos, criterio);
                break;
            case "InsertionSort":
                InsertionSort is = new InsertionSort();
                is.insertionSort(jogos, criterio);
                break;
        }

        long tempoFim = System.currentTimeMillis();
        long tempoExecucao = tempoFim - tempoInicio;

        // Atualizar tabela com jogos ordenados
        modeloTabela.setRowCount(0);
        for (Jogo jogo : jogos) {
            modeloTabela.addRow(new Object[]{
                Integer.valueOf(jogo.getId()),
                jogo.getTitulo(),
                jogo.getGenero(),
                Integer.valueOf(jogo.getAnoLancamento())
            });
        }

        mostrarStatus("Ordenação concluída com " + algoritmo + " por " + criterio + " em " + tempoExecucao + " ms", false);
    }

    private void atualizarTabela() {
        modeloTabela.setRowCount(0);
        Jogo[] jogos = biblioteca.exportarParaVetor();
        for (Jogo jogo : jogos) {
            modeloTabela.addRow(new Object[]{
                Integer.valueOf(jogo.getId()),
                jogo.getTitulo(),
                jogo.getGenero(),
                Integer.valueOf(jogo.getAnoLancamento())
            });
        }
    }

    private void limparCampos() {
        campoId.setText("");
        campoTitulo.setText("");
        campoGenero.setText("");
        campoAno.setText("");
    }

    // Método utilitário: define background e escolhe automaticamente foreground com contraste
    private void aplicarCorComContraste(JButton btn, Color bg) {
        btn.setBackground(bg);
        // calcular brilho perceptual: fórmula ITU-R BT.601
        int brilho = (int) ((bg.getRed() * 299 + bg.getGreen() * 587 + bg.getBlue() * 114) / 1000.0);
        Color fg = (brilho > 125) ? Color.BLACK : Color.WHITE;
        btn.setForeground(fg);
        btn.setOpaque(true);
        btn.setBorderPainted(false);
    }

    private void mostrarStatus(String mensagem, boolean isError) {
        statusLabel.setText(mensagem);
        if (isError) {
            statusLabel.setForeground(Color.RED);
        } else {
            statusLabel.setForeground(new Color(0, 128, 0));
        }
    }

    public static void main(String[] args) {
        System.out.println("=== INICIANDO BIBLIOTECA DE JOGOS ===");

        // Configurações para macOS
        System.setProperty("apple.laf.useScreenMenuBar", "true");
        System.setProperty("com.apple.mrj.application.apple.menu.about.name", "Biblioteca de Jogos");

        SwingUtilities.invokeLater(() -> {
            // Look and feel padrão está OK

            System.out.println("Criando interface...");
            BibliotecaSwingApp app = new BibliotecaSwingApp();
            app.setVisible(true);

            // Forçar janela para frente no macOS
            app.toFront();
            app.requestFocus();

            System.out.println("Interface exibida!");
        });
    }
}
