using System.Drawing; 
namespace PyxisKapriBack.Services.Interfaces
{
    public interface IFileService
    {
        public byte[] ImageToByteArray(Image imageIn);

        public string GetFileName(string imagePath);

        public string ConvertStringToBase64(string str);

        public string GetDefaultProfileImage();

        public byte[]? ConvertImageToByte(string image);
    }
}
