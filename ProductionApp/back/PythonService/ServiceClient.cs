using Newtonsoft.Json;
using PyxisKapriBack.Models;
using PyxisKapriBack.PythonService.Models;
using System.Net.Http.Headers;
using System.Text;

namespace PyxisKapriBack.PythonService
{
    public class ServiceClient
    {
        private readonly HttpClient client;
        private readonly IConfiguration configuration;

        private readonly string uri;// = "http://147.91.204.115:";
        //private readonly int port = 10036;

        public ServiceClient(HttpClient client, IConfiguration configuration)
        {
            this.client = client;
            this.configuration = configuration;

            uri = configuration.GetSection("PyService_Local_Config").GetSection("http").Value.ToString() + configuration.GetSection("PyService_Local_Config").GetSection("host").Value.ToString() + ":" + configuration.GetSection("PyService_Local_Config").GetSection("port").Value.ToString() + "/";
            //uri = configuration.GetSection("PyService_Server_Config").GetSection("http").Value.ToString() + configuration.GetSection("PyService_Server_Config").GetSection("host").Value.ToString() + ":" + configuration.GetSection("PyService_Server_Config").GetSection("port").Value.ToString() + "/";
        }

        public async Task<String> GetMean()
        {
            var request = new HttpRequestMessage(HttpMethod.Get, uri);
            request.Headers.Accept.Add(new MediaTypeWithQualityHeaderValue("application/json"));

            using (var response = await client.SendAsync(request))
            {
                response.EnsureSuccessStatusCode();

                var content = await response.Content.ReadAsStringAsync();

                Console.WriteLine(content);
                return JsonConvert.DeserializeObject<string>(content);
            }
        }

        public async Task<string> SendPathToService(string path)
        {
            var model = new ImageDataModel{
                ImagePath = path
            };
            var content = new StringContent(JsonConvert.SerializeObject(model), Encoding.UTF8, "application/json");

            HttpResponseMessage httpResponse = await client.PostAsync(uri + "image", content);

            var result = await httpResponse.Content.ReadAsStringAsync();
            Console.WriteLine(uri);
            return JsonConvert.DeserializeObject<string>(result);

        }

        public async Task<bool> DoFacesExistOnImage(string path)
        {
            var model = new ImageDataModel
            {
                ImagePath = path
            };
            var content = new StringContent(JsonConvert.SerializeObject(model), Encoding.UTF8, "application/json");

            HttpResponseMessage httpResponse = await client.PostAsync(uri + "face-detect", content);

            var response = await httpResponse.Content.ReadAsStringAsync();

            Response data = JsonConvert.DeserializeObject<Response>(response);
            if (data != null && data.StatusCode == 1)
                return true;

            return false;

        }


    }
}
