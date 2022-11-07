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
    }
}
