using PyxisKapriBack.Services.Interfaces;
using System.Drawing;
namespace PyxisKapriBack.Services
{
    public class FileService : IFileService
    {
        public byte[] ImageToByteArray(Image image)
        {
            MemoryStream ms = new MemoryStream(); 
            image.Save(ms, image.RawFormat);
            return ms.ToArray();
        }
        public Image ByteArrayToImage(byte[] byteArray)
        {
            MemoryStream ms = new MemoryStream(byteArray, 0, byteArray.Length);
            ms.Write(byteArray, 0, byteArray.Length);
            Image image = Image.FromStream(ms, true);

            return image; 
        }

        public string GetFileName(string imagePath)
        {
            var sections = imagePath.Split('/');    

            return sections.Last();
        }

        public string ConvertStringToBase64(string str)
        {
            var byteArray = System.Text.Encoding.UTF8.GetBytes(str);

            return Convert.ToBase64String(byteArray);
        }

        public string GetDefaultProfileImage()
        {
            return Directory.GetFiles(Constants.Constants.IMAGE_PATH).FirstOrDefault();
        }

        public byte[]? ConvertImageToByte(string image)
        {
            return System.IO.File.ReadAllBytes(image);
        }
    }
}
