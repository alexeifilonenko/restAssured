public class Main {
    public static void main(String[] args) {
        ApiClient apiClient = new ApiClient();

        apiClient.putSessionToken();
        System.out.println("---------------------------------");
        apiClient.createClient();
        System.out.println("---------------------------------");
        apiClient.getClientById();
        System.out.println("---------------------------------");
        // apiClient.updateClient("5c9238330fb2d32bc288f1ea");
    }
}
