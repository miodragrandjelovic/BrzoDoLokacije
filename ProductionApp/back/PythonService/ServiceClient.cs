﻿using Newtonsoft.Json;
using PyxisKapriBack.PythonService.Models;
using System.Net.Http.Headers;
using System.Text;

namespace PyxisKapriBack.PythonService
{
    public class ServiceClient
    {
        private readonly HttpClient client;
        private readonly IConfiguration configuration;

        private readonly string uri;

        public ServiceClient(HttpClient client, IConfiguration configuration)
        {
            this.client = client;
            this.configuration = configuration;

            //uri = configuration.GetSection("ML_Server_Config:http") + configuration.GetSection("ML_Server_Config:host").Value.ToString() + ":" + configuration.GetSection("ML_Server_Config:port").Value.ToString() + "/";
        }

        public async Task<String> GetMean()
        {
            var request = new HttpRequestMessage(HttpMethod.Get, "http://127.0.0.1:8000/");
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

            HttpResponseMessage httpResponse = await client.PostAsync("http://127.0.0.1:8000/image", content);

            var result = await httpResponse.Content.ReadAsStringAsync();
            return JsonConvert.DeserializeObject<string>(result);

        }



    }
}