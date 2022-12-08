namespace PyxisKapriBack.Models
{
    public class Response
    {
        public int StatusCode { get; set; }
        public string Message { get; set; } = string.Empty;
        public List<object> Data { get; set; } = null;
    }
}
