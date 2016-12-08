package com.argentruck.argentruck_partner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Repositorio ficticio de Clients
 */
public class ClientsRepository {
    private static ClientsRepository repository = new ClientsRepository();
    private HashMap<String, Client> Clients = new HashMap<>();

    public static ClientsRepository getInstance() {
        return repository;
    }

    private ClientsRepository() {
        saveClient(new Client("Alexander Pierrot", "CEO", "Insures S.O.", R.drawable.lead_photo_1));
        saveClient(new Client("Carlos Lopez", "Asistente", "Hospital Blue", R.drawable.lead_photo_2));
        saveClient(new Client("Sara Bonz", "Directora de Marketing", "Electrical Parts ltd", R.drawable.lead_photo_3));
        saveClient(new Client("Liliana Clarence", "Diseñadora de Producto", "Creativa App", R.drawable.lead_photo_4));
        saveClient(new Client("Benito Peralta", "Supervisor de Ventas", "Neumáticos Press", R.drawable.lead_photo_5));
        saveClient(new Client("Juan Jaramillo", "CEO", "Banco Nacional", R.drawable.lead_photo_6));
        saveClient(new Client("Christian Steps", "CTO", "Cooperativa Verde", R.drawable.lead_photo_7));
        saveClient(new Client("Alexa Giraldo", "Client Programmer", "Frutisofy", R.drawable.lead_photo_8));
        saveClient(new Client("Linda Murillo", "Directora de Marketing", "Seguros Boliver", R.drawable.lead_photo_9));
        saveClient(new Client("Lizeth Astrada", "CEO", "Concesionario Motolox", R.drawable.lead_photo_10));
    }

    private void saveClient(Client Client) {
        Clients.put(Client.getId(), Client);
    }

    public List<Client> getClients() {
        return new ArrayList<>(Clients.values());
    }
}

