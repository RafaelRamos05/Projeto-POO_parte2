package ceub;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.ArrayList;

public class TelaPrincipal extends JFrame {

    private static final long serialVersionUID = 1L;

    // Modelos e Listas
    private final DefaultListModel<Cliente> clientesModel = new DefaultListModel<>();
    private final JList<Cliente> clientesList = new JList<>(clientesModel);

    private final DefaultListModel<Pet> petsModel = new DefaultListModel<>();
    private final JList<Pet> petsList = new JList<>(petsModel);

    private final DefaultListModel<Servico> servicosModel = new DefaultListModel<>();
    private final JList<Servico> servicosList = new JList<>(servicosModel);

    // Lista principal de dados
    private final java.util.List<Cliente> clientes = new ArrayList<>();

    public TelaPrincipal() {
        setTitle("PetShop - Sistema Completo");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 700);
        setLocationRelativeTo(null);

        JPanel content = new JPanel(new BorderLayout(10, 10));
        content.setBorder(new EmptyBorder(10, 10, 10, 10));
        setContentPane(content);

        // Cabeçalho
        JLabel lbl = new JLabel("Sistema PetShop - Gestão");
        lbl.setFont(lbl.getFont().deriveFont(Font.BOLD, 18f));
        lbl.setHorizontalAlignment(SwingConstants.CENTER);
        content.add(lbl, BorderLayout.NORTH);

        // Divisão Principal
        JSplitPane split = new JSplitPane();
        split.setDividerLocation(350);
        content.add(split, BorderLayout.CENTER);

        // --- PAINEL ESQUERDO: CLIENTES ---
        JPanel left = new JPanel(new BorderLayout(5, 5));
        left.setBorder(BorderFactory.createTitledBorder("Clientes"));

        clientesList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        clientesList.addListSelectionListener(e -> onClienteSelected());
        left.add(new JScrollPane(clientesList), BorderLayout.CENTER);

        // Botões Clientes
        JPanel leftButtons = new JPanel(new GridLayout(0, 1, 5, 5));
        JButton btnAddCliente = new JButton("Cadastrar Cliente + Pet");
        btnAddCliente.addActionListener(e -> onCadastrarCliente());
        JButton btnBuscarCliente = new JButton("Buscar Cliente (Nome/CPF)");
        btnBuscarCliente.addActionListener(e -> onBuscarCliente());
        JButton btnRemoverCliente = new JButton("Remover Cliente");
        btnRemoverCliente.addActionListener(e -> onRemoverCliente());

        leftButtons.add(btnAddCliente);
        leftButtons.add(btnBuscarCliente);
        leftButtons.add(btnRemoverCliente);
        left.add(leftButtons, BorderLayout.SOUTH);

        split.setLeftComponent(left);

        // --- PAINEL DIREITO: ABAS ---
        JTabbedPane tabs = new JTabbedPane();

        // 1. ABA PETS (Busca Global)
        JPanel painelPets = new JPanel(new BorderLayout(5, 5));
        painelPets.setBorder(new EmptyBorder(5, 5, 5, 5));

        // Área de Busca de Pets
        JPanel buscaPetPanel = new JPanel(new BorderLayout(5, 5));
        JTextField txtBuscaPet = new JTextField();
        JButton btnBuscaPet = new JButton("Buscar Pet");
        btnBuscaPet.addActionListener(e -> onBuscarPet(txtBuscaPet.getText()));
        
        buscaPetPanel.add(new JLabel("Nome do Pet:"), BorderLayout.WEST);
        buscaPetPanel.add(txtBuscaPet, BorderLayout.CENTER);
        buscaPetPanel.add(btnBuscaPet, BorderLayout.EAST);
        painelPets.add(buscaPetPanel, BorderLayout.NORTH);

        petsList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        painelPets.add(new JScrollPane(petsList), BorderLayout.CENTER);

        JPanel petBtns = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton btnAddPet = new JButton("Adicionar Pet");
        btnAddPet.addActionListener(e -> onAdicionarPet());
        JButton btnRemoverPet = new JButton("Remover Pet");
        btnRemoverPet.addActionListener(e -> onRemoverPet());
        
        petBtns.add(btnAddPet);
        petBtns.add(btnRemoverPet);
        painelPets.add(petBtns, BorderLayout.SOUTH);

        tabs.addTab("Pets", painelPets);

        // 2. ABA SERVIÇOS (Sem busca, conforme pedido)
        JPanel painelServicos = new JPanel(new BorderLayout(5, 5));
        painelServicos.setBorder(new EmptyBorder(5, 5, 5, 5));

        servicosList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        painelServicos.add(new JScrollPane(servicosList), BorderLayout.CENTER);

        JPanel servBtns = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton btnContratar = new JButton("Contratar Serviço");
        btnContratar.addActionListener(e -> onContratarServico());
        JButton btnCancelar = new JButton("Cancelar Serviço");
        btnCancelar.addActionListener(e -> onCancelarServico());
        
        servBtns.add(btnContratar);
        servBtns.add(btnCancelar);
        painelServicos.add(servBtns, BorderLayout.SOUTH);

        tabs.addTab("Serviços", painelServicos);

        split.setRightComponent(tabs);

        JLabel status = new JLabel("Pronto");
        content.add(status, BorderLayout.SOUTH);
    }

    // --- MÉTODOS DE CLIENTE ---

    private void onCadastrarCliente() {
        JTextField nome = new JTextField();
        JTextField telefone = new JTextField();
        JTextField email = new JTextField();
        JTextField cpf = new JTextField();

        JPanel form = new JPanel(new GridLayout(0, 1));
        form.add(new JLabel("Nome:")); form.add(nome);
        form.add(new JLabel("Telefone (Ex: (61) 99999-9999):")); form.add(telefone);
        form.add(new JLabel("Email:")); form.add(email);
        form.add(new JLabel("CPF (use números e/ou hífen):")); form.add(cpf);

        int res = JOptionPane.showConfirmDialog(this, form, "Cadastrar Cliente", JOptionPane.OK_CANCEL_OPTION);
        if (res != JOptionPane.OK_OPTION) return;

        try {
            Cliente c = new Cliente(nome.getText(), telefone.getText(), email.getText(), cpf.getText());
            if (cadastrarPetParaCliente(c)) {
                clientes.add(c);
                clientesModel.addElement(c);
                JOptionPane.showMessageDialog(this, "Cliente cadastrado com sucesso.");
            } else {
                JOptionPane.showMessageDialog(this, "Cadastro cancelado: Cliente precisa de um Pet.");
            }
        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(this, "Erro de Validação: " + ex.getMessage(), "Atenção", JOptionPane.WARNING_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro genérico: " + ex.getMessage());
        }
    }

    private void onBuscarCliente() {
        String q = JOptionPane.showInputDialog(this, "Digite o Nome ou CPF para busca:");
        if (q == null || q.trim().isEmpty()) return;

        DefaultListModel<Cliente> tmp = new DefaultListModel<>();
        String termo = q.toLowerCase();

        for (Cliente c : clientes) {
            boolean achouNome = c.getNome().toLowerCase().contains(termo);
            boolean achouCpf = c.getCpf().contains(q); 

            if (achouNome || achouCpf) {
                tmp.addElement(c);
            }
        }
        
        if (tmp.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Nenhum cliente encontrado com esse Nome ou CPF.");
        } else {
            JList<Cliente> jl = new JList<>(tmp);
            jl.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            JOptionPane.showMessageDialog(this, new JScrollPane(jl), "Resultados da Busca", JOptionPane.PLAIN_MESSAGE);
        }
    }

    private void onRemoverCliente() {
        int idx = clientesList.getSelectedIndex();
        if (idx == -1) { JOptionPane.showMessageDialog(this, "Selecione um cliente."); return; }
        Cliente c = clientesModel.get(idx);

        if (!c.getServicos().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Não é possível remover: cliente possui serviços contratados.");
            return;
        }

        boolean petComServicos = false;
        for (Pet p : c.getPets()) {
            if (!c.buscarServicosPorPet(p).isEmpty()) {
                petComServicos = true;
                break;
            }
        }

        if (petComServicos) {
            JOptionPane.showMessageDialog(this, "Não é possível remover: há pets com serviços vinculados.");
            return;
        }

        clientes.remove(c);
        clientesModel.remove(idx);
        petsModel.clear();
        servicosModel.clear();
        JOptionPane.showMessageDialog(this, "Cliente removido.");
    }

    private void onClienteSelected() {
        Cliente c = clientesList.getSelectedValue();
        petsModel.clear();
        servicosModel.clear();
        if (c == null) return;

        for (Pet p : c.getPets()) petsModel.addElement(p);
        for (Servico s : c.getServicos()) servicosModel.addElement(s);
    }

    // --- MÉTODOS DE PET ---

    private void onBuscarPet(String nomeBusca) {
        petsModel.clear();
        String termo = (nomeBusca != null) ? nomeBusca.trim().toLowerCase() : "";

        if (termo.isEmpty()) {
            for (Cliente c : clientes) {
                 for (Pet p : c.getPets()) petsModel.addElement(p);
            }
            return;
        }

        boolean encontrou = false;
        for (Cliente c : clientes) {
            for (Pet p : c.getPets()) {
                if (p.getNome().toLowerCase().contains(termo)) {
                    petsModel.addElement(p);
                    encontrou = true;
                }
            }
        }
        
        if (!encontrou) {
            JOptionPane.showMessageDialog(this, "Nenhum pet encontrado com esse nome no sistema.");
        }
    }

    private boolean cadastrarPetParaCliente(Cliente c) {
        JTextField pNome = new JTextField();
        JTextField raca = new JTextField();
        JTextField idade = new JTextField();
        JTextField peso = new JTextField();
        JTextField sexo = new JTextField();

        JPanel formPet = new JPanel(new GridLayout(0, 1));
        formPet.add(new JLabel("Nome do Pet:")); formPet.add(pNome);
        formPet.add(new JLabel("Raça:")); formPet.add(raca);
        formPet.add(new JLabel("Sexo (F/M):")); formPet.add(sexo);
        formPet.add(new JLabel("Idade (anos):")); formPet.add(idade);
        formPet.add(new JLabel("Peso (kg):")); formPet.add(peso);

        while (true) {
            int rp = JOptionPane.showConfirmDialog(this, formPet, "Cadastrar Pet", JOptionPane.OK_CANCEL_OPTION);
            if (rp != JOptionPane.OK_OPTION) return false;

            try {
                int id = parseIntSafe(idade.getText(), -1);
                double w = parseDoubleSafe(peso.getText(), -1);
                String sTexto = sexo.getText().trim();
                char sChar = sTexto.isEmpty() ? ' ' : sTexto.charAt(0);

                Pet pet = new Pet(pNome.getText(), raca.getText(), id, w, sChar);
                c.adicionarPet(pet);
                
                if (clientesList.getSelectedValue() == c) {
                    petsModel.addElement(pet);
                }
                return true;
            } catch (IllegalArgumentException ex) {
                JOptionPane.showMessageDialog(this, "Erro no Pet: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void onAdicionarPet() {
        Cliente c = clientesList.getSelectedValue();
        if (c == null) {
            JOptionPane.showMessageDialog(this, "Selecione um cliente na lista esquerda para adicionar um pet a ele.");
            return;
        }
        if (cadastrarPetParaCliente(c)) {
            JOptionPane.showMessageDialog(this, "Pet adicionado.");
        }
    }

    private void onRemoverPet() {
        Pet petSelecionado = petsList.getSelectedValue();
        
        if (petSelecionado == null) {
            JOptionPane.showMessageDialog(this, "Selecione um pet na lista para remover.");
            return;
        }

        Cliente dono = null;
        for (Cliente c : clientes) {
            if (c.getPets().contains(petSelecionado)) {
                dono = c;
                break;
            }
        }
        
        if (dono == null) {
             JOptionPane.showMessageDialog(this, "Erro: Dono do pet não encontrado.");
             return;
        }

        int indexNoCliente = dono.getPets().indexOf(petSelecionado);
        boolean ok = dono.removerPet(indexNoCliente);
        
        if (ok) {
            petsModel.removeElement(petSelecionado);
            JOptionPane.showMessageDialog(this, "Pet removido do cliente " + dono.getNome() + ".");
            
            if (clientesList.getSelectedValue() == dono) {
                onClienteSelected(); 
            }
        } else {
            JOptionPane.showMessageDialog(this, "Não foi possível remover: pet possui serviços vinculados.");
        }
    }

    // --- MÉTODOS DE SERVIÇO ---

    private void onContratarServico() {
        Cliente c = clientesList.getSelectedValue();
        if (c == null) { JOptionPane.showMessageDialog(this, "Selecione um cliente."); return; }
        if (c.getPets().isEmpty()) { JOptionPane.showMessageDialog(this, "Cliente não tem pets cadastrados."); return; }

        Pet pet = (Pet) JOptionPane.showInputDialog(this, "Escolha o pet:", "Pet",
                JOptionPane.PLAIN_MESSAGE, null, c.getPets().toArray(), c.getPets().get(0));
        if (pet == null) return;

        String[] opcoes = {
            "Banho e Tosa (R$ 80)", 
            "Consulta Veterinária (R$ 150)",
            "Hospedagem (R$ 100)",
            "Adestramento (R$ 120)"
        };

        String tipo = (String) JOptionPane.showInputDialog(this, "Escolha o serviço:", "Serviço",
                JOptionPane.PLAIN_MESSAGE, null, opcoes, opcoes[0]);
        if (tipo == null) return;

        String data = JOptionPane.showInputDialog(this, "Data (dd/MM/yyyy):", "");
        if (data == null) return;

        try {
            Servico s;
            if (tipo.startsWith("Banho")) {
                s = new BanhoETosa(data, pet);
            } else if (tipo.startsWith("Consulta")) {
                s = new ConsultaVeterinaria(data, pet);
            } else if (tipo.startsWith("Hospedagem")) {
                s = new Hospedagem(data, pet);
            } else {
                s = new Adestramento(data, pet);
            }

            c.adicionarServico(s);
            servicosModel.addElement(s);
            JOptionPane.showMessageDialog(this, "Serviço contratado.");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro: " + ex.getMessage());
        }
    }

    private void onCancelarServico() {
        Cliente c = clientesList.getSelectedValue();
        Servico s = servicosList.getSelectedValue();

        if (c == null || s == null) { JOptionPane.showMessageDialog(this, "Selecione cliente e serviço."); return; }

        int indexReal = c.getServicos().indexOf(s);

        boolean ok = c.cancelarServico(indexReal);
        if (ok) {
            servicosModel.removeElement(s);
            JOptionPane.showMessageDialog(this, "Serviço cancelado.");
        } else {
            JOptionPane.showMessageDialog(this, "Não foi possível cancelar.");
        }
    }

    // Utils
    private int parseIntSafe(String s, int def) {
        try { return Integer.parseInt(s.trim()); } catch (Exception e) { return def; }
    }
    private double parseDoubleSafe(String s, double def) {
        try { return Double.parseDouble(s.trim()); } catch (Exception e) { return def; }
    }

    public static void main(String[] args) {
        try { UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName()); } catch (Exception ignored) {}
        EventQueue.invokeLater(() -> {
            TelaPrincipal t = new TelaPrincipal();
            t.setVisible(true);
        });
    }
}