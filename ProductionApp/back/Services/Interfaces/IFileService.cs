using System.Drawing; 
namespace PyxisKapriBack.Services.Interfaces
{
    public interface IFileService
    {
        public byte[] ImageToByteArray(Image imageIn);

        public string GetFileName(string imagePath);
    }
}
