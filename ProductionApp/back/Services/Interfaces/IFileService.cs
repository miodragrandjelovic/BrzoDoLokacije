using System.Drawing; 
namespace PyxisKapriBack.Services.Interfaces
{
    public interface IFileService
    {
        public byte[] ImageToByteArray(Image imageIn);

        public string GetFileName(string imagePath);

        public string ConvertStringToBase64(string str);

        public string? GetDefaultProfileImage();

        public byte[]? ConvertImageToByte(string image);

        public bool CheckIfFolderExists(string path);
        public bool CreateFolder(string folderName);

        public void AddFile(string path, IFormFile file);

        public IFormFile GetFile(string path, string fileName);

        public byte[] GetUserProfileImage(string path);
 
        public bool UpdateFile(string path, IFormFile file);

        public string GetDefaultPath(string folderName);
    }
}
