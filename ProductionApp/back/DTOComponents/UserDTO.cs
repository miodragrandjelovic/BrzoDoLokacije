﻿using PyxisKapriBack.Models;

namespace PyxisKapriBack.DTOComponents
{
    public class UserDTO
    {
        public string Username { get; set; } = string.Empty;
        public string Password { get; set; } = string.Empty;
        public string FirstName { get; set; } = string.Empty;
        public string LastName { get; set; } = string.Empty;
        public string Email { get; set; } = string.Empty;
        public string ProfileImagePath { get; set; } = string.Empty;
        public int UserFollowersCount { get; set; } = 0;    
        public int UserFollowingCount { get; set; } = 0;
        public int DifferentLocations { get; set; } = 0;
        public double AverageGradeForAllPosts { get; set; } = 0;
    }
}
