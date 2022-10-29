﻿using PyxisKapriBack.Services.Interfaces;

namespace PyxisKapriBack.Services
{
    public class FileService : IFileService
    {
        public byte[] ImageToByteArray(System.Drawing.Image imageIn)
        {
            using (var ms = new MemoryStream())
            {
                imageIn.Save(ms, imageIn.RawFormat);
                return ms.ToArray();
            }
        }
    }
}
