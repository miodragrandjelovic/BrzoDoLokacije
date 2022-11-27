﻿namespace PyxisKapriBack.Constants
{
    public static class Constants
    {
        public const String USER_PATH = "";
        public const String UNKNWOWN = "Nepoznato";

        // JWT Constants

        public const string FIRST_NAME = "FirstName";
        public const string LAST_NAME = "LastName";

        // Roles Constants 
        public const string ADMIN     = "Admin"; 
        public const string USER      = "User";
        public const string MODERATOR = "Moderator";
        public const int ALL_ROLES = 0;


        // DefaultImage path
        public const string IMAGE_PATH = @"\Images\DefaultProfileImage\";
        public const string IMAGE_PATH_LINUX = @"/Images/DefaultProfileImage/";

        public const string DEFAULT_IMAGE = @"iVBORw0KGgoAAAANSUhEUgAAAPAAAADwCAYAAAA+VemSAAAACXBIWXMAAA7EAAAOxAGVKw4bAAAgAElEQVR42u2de5AkVZ3vz/llVXU9uqqra7rb7p6xA8dZnDvABLI+WOUCO7JcDA1E1vAqq67C4t0gXPeus3I1VjFcgzA22LmGL24g6xV8IRgKuHBdlQUcZhTkITsNbc9cYN2Z6cdUdVdmvTKz8pzzO/cPquf2DN0z3dMn82Rl5ec/mJmT3/PL/NbJPI/fj5KYUDM9PZ3dvHnzRDKZnACALYSQcUrpGCFkCACKhJCSlLIIAFlE7CeE9AMAUEpPaEdKSRARCSFNAGgiok0ptQghVUS0CCELUso5QsgsIh5ljB2emZk5vH37dlt3DGJWh268iRgVlMvlLYVC4VzDMHYQQs4BgG1SyrMBYPRkMwZFx/TzlNJDiPgCIeR5IcRUvV5/bmRk5KjumMXEBtZCuVweHRgYuBAA3kwpfQOl9HwAGNKtaz0g4oKU8lkp5VOI+ES9Xn98eHh4XreuXiM2cACYpjmRzWZ3GYZxCSHkYsMwturW5AdCiJcIIXuFEL+0bfvhwcHBw7o1RZ3YwD4wOzubLpVKuwzDeDul9HLDMM7WrUkHQohDUsqfCyF+Wq1WHx4fH3d1a4oasYEVsbCwUCoUClcCwLsB4DJKaVa3pjAhpbQR8SFEvLder/9kaGioqltTFIgNvAHK5XKhWCxeDQDvB4BdlNKEbk3dgJSSI+LDiHiXZVk/HhkZqevWFNMjTE5OguM4V3DO70ZER8ZsCER0OOd3O45zxeTkJOi+v91GPAKvkVqtNpHJZK4zDOPaznpsjGIQ8Sgi/m/btr85MDAQT4CtgdjAp8G27V2pVOqvAOCd8StyMMiXX7Ef8Dzvq9ls9mHdesJMbOAVmJubS5VKpWsSicTfAMBO3Xp6GSHEc5zzPaZpfn9sbMzTrSdsxAZeRrlc7i8Wix81DGM3AIzr1hPz/0HEWSHEHsuyvjEyMtLUrScsxAYmhFQqlf6BgYGPdYzbVTuieg1EXBBC7KnVal8bHh7ueSP3tIHn5ubSmzZtusEwjE/Hxu0uOkb+4uLi4q1jY2M9u0GkJw38/PPPw7Zt2z6cSCQ+H88odzeIeJRz/rkXXnjhjnPOOQd16wmanjOw4ziXJZPJPYZhxJNTEUIIcYAxtjuTyTykW0uQ9IyBG43G1kwms8cwjKt0a4nxDyHEfY7j7M7n8y/p1hIEkd/5MjMzk/I87zO5XO752LzRxzCMq3K53POe531mZmYmpVuP30R6BHYc5+JUKnU7APTkaaBeBxEPeZ53fSaT2atbi19EcgSuVqsFxtht6XT6kdi8vQsAnJ1Opx9hjN1WrVYLuvX4QeRGYMdxLkulUt8EgAndWlSCiHUp5UuEkMOEkMOIOEcIKRNCykKIqud5TQCwGGPe4cOHvVarZXPOPUIISSQSqVwul52YmEglk8kUIhZTqVS/YRglQsgIIWQEAMYIIROEkAlK6VYAiNQDj4iHPc+7LmqTXJExcKVSSReLxVsSicQNpIvfLBDRklI+Swg5gIiTjLHper0+PTY2thCkjrm5uaFCobA9mUxuB4DzCCE7O6l/irpjtAGQc36rZVmfHB4ejsTacSQM3Gq1dqbT6bsAYIduLetBSkmklAcQcR8i/tpxnN8Ui8VDunWdCsuyzs5kMm8CgD8CgIsopTt1Jd07UxBxynXd9+dyuQO6tfQ8ruve0E3ncjnn/8E5v63dbl997Nixrt/9dezYsaF2u3015/w2zvl/6I7vWkFEx3XdG3THr2dZXFwsMMbu1v0grOFBkZzzJxhjn6rX6+fqjpvf1Ov1cz3P+xTn/AlE1B3+08IYu3txcTFS3/uhp9ls7hBC/E73zT8VnPOnPc/7W8uyIjWZth5M05zwPO9vOedP674fp0II8btms9lVn19di+u6VyFiQ/dNX+VBOMYYu6XRaMQPw0k0Go0djLFbhBDHdN+nlUDEhuu68UYfP2m32zdJKYXum30ynPN/dV33PUeOHIkzdpyGI0eOJFzXfQ/n/F9137cVEO12+ybdMYoc5XI5zRi7S/fdXQ4iOoyx25vNZuS/a/2i2Wyeyxi7PWyTkIyxu8rlclp3fCJBvV4f4pzv131Tl0BEkzF2s2VZI7pjExUsyxphjN2MiKbu+7sE53x/vV7v+lUCrTQajbOEEAd130wpjxv3JtM0u3kjQ6gxTbPIGLspLEYWQhxsNBpn6Y5LV9JqtXYi4ozum4iILc/zbo6NGxymaRY9z7sZEVshuP8zrVYrPju+HlzXvTAEv8KCMXZ7rVYb1R2PXqVWq40yxm6XmicuEdF0XfdC3fHoClqt1i7dy0Sc80fiX93w0Gq1dnLOH9Fs4kar1dqlOxahxnGcy3XOSAohZlzXfZ/uOMSsjOu67xNCaPusQkTHcZzLdcchlNi2rdO8gjH29aieGY0SnbPeX5eaXqtjE69A57VZi3mFEAdt275Idwxi1odt2xfpWqFARCd+ne7QmbDS8c0rGGNfrlQqcR3fLqVSqWQ9z/uy1DAad7Ze9vbEVmepKPDZZiHEjOM4l+nuf4waHMe5TMe3MSKaPTvZ2Wg0ztKxzss5fzDeYRM9Ojv2HtRg4pme2+xRr9eHgv5+QUTmed6N+/bt69p0OzGnZt++feB53o2IyIJ8toQQB3tmUCiXy+mg9zYj4jHHcS7V3feYYHAc51JEDPTYIud8f6VSif4BiKBPFXHOn242mz17qL5XaTabE0EnEmCM3aW7377ied5NAQf0R9VqNZ5l7lGq1WqWMfajIJ85z/OieZ7Ydd2rZIDT/Z7n/cNTTz0Vf+/2OE8++SR4nvcPAXpYRC6zR6vV2hHgWq9ot9txtsGYE2i32zfIgAaQzr7paKRVWlhYKASVgA4R247jvFd3n2PCieM470XEdhDPohDidwsLC92/NTeo1K+I2Ir3qMacjs6BmUDOGTPG7tbd3w3huu4NAZm3Ydv2pbr7G9Md2LZ9aVCfdF2bPL6zTdL3AwqdPanxYYSYdeG67kVBmLhz8MG37Za+FLWpVCrpUqn0tN+1iqSUtuu678hms4/6eZ0wUqlUxvP5/PmGYewghLyOUnoWpXSUEDIkpSwBQGqpZpGUkiCiRymtEkIWpJTzUsrfE0IOCiGmGo3Gs8PDw7O6+xQ0tm1fmk6nH6SU+rrUiIhT1Wr1D7umoBpj7KsB/LK1e+mb1zTN0Xa7fS3n/C7O+RHV8RRCHOGcf6/dbl9rmmbPpBFyHOdyKaXvE1uMsa/q7utaA3KZ9H+6XvTCbLNpmqV2u/0xzvn+IOsMIaLgnO9vt9sfM02zpDsOfuM4znsDembDfQKuWq0WhBC+V6iL+jpvq9U6n3P+naCWPE4FIjqc8zubzeb5uuPiJ511Yl8RQvxHqLO+MMZu8zsInufdoruffmHb9gU6jsStFc75g7ZtX6A7Tn7hed4tfseQMXab7n6uiOM4F0ufX0MYYz968sknI7c9sl6vjzPGvuN3/BQhGGN31uv1yH0nP/nkkxDA3mnhOM7Fuvt6AjMzMym/z/dyzn9rmmbkDia02+2/QMSazw+Ncjq5kq/VHT/VVKvVLOf8t37GTghxcGZmJqW7r8fxPO8zPj8sx6J2JLBWq5UYY//sZ9yCgDF2r2VZkZroajabE36fJ/Y87zO6+0kIIaTRaGz1c8MGIrKoHca3bfuCICb7gkII8e+2bUcqN1QnKYBvmT0Q0Wk0Glt195Nwzu/18+HwPO9G3X1Uieu67wxDzR/VIGLDcZwrdMdXJZ7n3ehnzBhj92rtYGfN1zc45w9GKYeV67rXBJ2vKUgQsW3b9nt0x1kV+/btA79XBbStDU9NTQHn/N/86pgQYiZKicIcx3kfInbDLPOGQEQWpQPtnQSMvmVPFUL829TUVPCDVLvdvtbH5yD8u1bWQecIW2RH3pPplB8J11LJxu6fr7sL2+12sLP5c3NzaSGE8v24SzDGvqz7pqmik42k65aJNgoiLjabTf2TNIpgjH3Zr1gJIY7Mzc0Fl9HS87xP+NiZg1Epd7K4uNgfVDaSMCKE+O38/HwkUq1WKpWsn3sdPM/7RFAd6RdCVPy651EqNMYYu9OvG94teJ4Xmbcp27Yvkj69SgshKpVKpd/3Tnie9ym/bjZj7Ou6b5IqXNe90jdXdBciSuv4ndKmvuB53qd8FV8ul30bfYUQM6E+qbEOqtVqv59zBN1G6LYObuzeFvyalRZCVMrl8rpG4XVNXxeLxY8CgC9LO4yx3aVSqe5n8IOiv7//swCwRbeOsAAAZw8PD39ctw4VlEqlOmNstx9tA8BQsVj8qC/C5+bmUn798nDOH/E16gHSaDS26CpWHmYQ0TRNs6j7/qiCc/6IH3ESQszMzs6u+W1lzSNwqVS6BgDGfYgFttvtvw4k6gGQTqf/jlIaiZlXlVBKi/39/ZEYhQkhpPPMoup2AWB806ZN1ygXLISY9OMXhzF2exABDwLLskbi0Xd1hBCL8/PzkVgiJIQQxtjtPsVpcq0a1jQC27a9CwDOVR0AKaVt2/Zngwy6n2Sz2b+MR9/VAYDS4ODgB3TrUIVt25+VUtqq2wWAc23b3rWmv7uWv5RKpf7KjwBwzr8yMDAw70fbQTM5OZkwDON63TrCjmEY/023BlUMDAzMc86/4kfbqVRKzWdlrVab8GMfb9QmNTrpSWPWQLPZjEbhL0KIaZpFRDRVxwgRWa1WO20Si9OOwNls9jpKaUJ1x4UQXxocHLR0BV41iUTiz3Rr6Bb6+vrer1uDKgYHBy0hxJdUt0spTWSz2dO+0Z3SwJOTkwAAyk9KSCmtZrPpy6uHDg4cOJAAgHfq1tEtUEqv1K1BJc1m8ytSSuWDEQB8eHJy8pSD5ykNvG3btsv92JAghLg1SqPvtm3b3gQAkcoL5SeGYey0LCsyG106o/CtqtsFgC3btm075bHaUxo4mUx+RLUoKaXbarUis8GdEEKSyWRkzi4HRTqdXtMsa7fQarW+LKVUXvvodB5c1cDlcrkAAMpfdYQQ3y0Wi2XV7eqEUnqJbg3dRiKR+M+6NaikWCyWhRDfVd0uAFxZLpdXPSOwqoGLxeLVfqxpttvtSI2++/fvB0rpm3Tr6EIiFzM/nm1KabpYLF692p+vamAAUD5TKIR4uL+//znV7erkvPPO2wYA/p/jjBgAsGNycjISJ5SW6O/vf04I8bDqdk/lxRUNvLCwUAIA5d8onPP/pbpN3aRSqUjlQw4KSmli69at23TrUI0fzzgA7FpYWFhxknRFAxcKhStVr/0iYrlcLt+nunO6MQwjcg9hUADAdt0aVFOpVO5DRKVzPJTSRKFQWHE+akUDA8C7VXcMEb89MTHBVbcbAv5At4BuxTCMs3RrUM2rX/1qjojfVt3uap58hYHn5ubSAKB8WcR13W+pbjMMUEojs54ZNJTSV+vW4Ad+POsAcNlKmStfYeDBwcFdlFKlR76EEM/k8/kp1Z0KA5TSEd0aupWoxi6fz08JIZ5R2SalNDs4OPiKealXGNgwjLer7hAi3qW6zRARyYcwCCilkTnMcjJ+PPMrefMVBqaUXq7yolJKYtv2Pao7ExaklPES0hmCiJHdfmrb9j1SSqVtruTNEwxsmuaEYRhnq7woIv6mWCwe9iVK4SASmTR1AACRWgdeTrFYPIyIv1HZpmEYZ5umecIRwxMMnM1mla/9Sin1llD0GYDIFE8MnKjHzo9n/2SPnhBBwzCU7+l1HOcB9aEJD5RS3RJiQoofz/7JHj35J1BpRTkhxOFCoRCprZMno/o7p5eIeuwKhcJzQgjVn48nePS4gSuVyqhhGKqryf2LX8EJC4ioPLVoryCEiHzspJRKPWAYxtZyuTy69N/HDVwoFC5ULV4I8TN/w6MfSmlkEhMEDQBEPnaIqNwDAwMDx7163MAA8GaVF5FSEsuy9vobHv1IH9KK9gqI2NStwW8sy9qr+lNhuVePG5hS+gaVF5FSHnjVq1614HuENEMpjVRygiDphdi96lWvWpBSHlDZ5nKvLjfw+Sovgoj7/A+PfqSUkX8I/aJXYqfaC8u9CoQQUi6Xt6iuOoiIvw4mPHqRUkYiMb0OpJQzujUEgWovAMBQuVzeQkjHwIVCQXnZFMdxHg8mPHqRUh7VraFbEUL0ROz88MKSZ4EQQgzDUJopHxGtYrH4QjDh0c6LugV0K4j4km4NQVAsFl9ARKUz7kueXfoGPkdl41LKZwOKjXYYY73yQ6UUKSU5cuRITxi401/VnjiHkI6BAUB1Whils25hptFoTOvW0I1IKV/avn278jzKIUapJ5Y8C4QQIqVUfQJpzfVNu53R0dEFIUTkl8tUI6WMZIKH1VDtiSXPwvT0dBYARjfa4HIYY702KvXMG4cqpJSR3iN/Mqo9AQCj09PTWdi8efOE6hM19Xq9pwzcS9/8qkDEp3VrCBLVnqCUks2bN09AMpk8bQ3S9YCI9bGxsZ56pUTEJ3Vr6DZs235Kt4YgGRsbW0DEuso2k8nkBKiuPiil7JmZxSVs2+6JNW9VIGJ5cHDw97p1BI1qbwDAFiCEjCvWGeX0OSsyODj4e0Sc1a2jW5BS9uoPnmpvjAOldCzkIruCXtn7rQJE/KVuDZpQ6g1K6RgQQlTvgZ4LNCQhQUr5iG4N3QJjLPLHTFfCB28MAQCozs3bEydMTsa2beVV6aIIIlanpqaUJj3vIpR6AwCKQAhRnZu3Jw1cLBYP+ZD/KHJIKR994xvfGPlUOqug2hslkFIqHYGFENUAAxIqVOc/iiJCiJ/q1qCx70q9IaUsAgAorYPkeV7k06SshhDin3VrCDOdKh3/R7cOXbTbbaXeAIAsIKLS0iCGYfTsCGya5kNxjqzVQcSnBgcHe3a5LZFIKPUGIvYDIUSpgVutlhdoVELE2NiYi4jxa/QqSCnv161BJ81mU7U3+gEU17eYm5uLYhHvNSOE+JFuDWHFdd0f69agk/n5eaXeAAAA1QcZms1mT79Cmqb5gJSyl865rgkhxFRUa0SvFdXeoJQS5dWlGGM9/fCOjo7WEbFnJ2pWI+I1oteE53nKvRHt8nCaYIx9T7eGsOG67g90a4giyg2cTCbTujulG9M0H0DEnp2NPxkhxOOFQqHnc4elUinl3gDVZR/6+/uVrit3I+Pj4x4ixiNOByHEnbo1hAHV3pBSElBdXW9sbCwRbFjCSbvd/qZuDWFASuk2Go34x4yo9wYiIhBClO4O6e/vTwUalZDS39//jBCiVzftHwcRfzw0NBT5KoRrwQdvNAEAlBqYMab6cETXIoS4TbcG3Xied7tuDWFBtTcAoAmIqHRtqq+vT+nOrm7GNM3vSymV5kHqJoQQ09ls9lHdOsKCam8gog2qC1QbhhGPwB1GR0ebQog7dOvQBSJ+XbeGMKHaG5RSCwghqpc7RoILSfhxXferUsqeO/8qpWzWarVv69YRMlR7owqqiy75ILKryefzLyDiA7p1BI0Q4o7h4eGe/XxYBaXeQEQLCCFKczgDwOZAQ9IFeJ63R7eGgEHXdb+sW0TY8MEbCyClVJ1oS2me6SiQzWb3CiF6JpG5EOIn+Xy+53derYDqHOxzQAhRfcBaaaWHqMA5/6JuDUHRbrdv0a0hpKj2xiwgotIq6ZTSrQEGpGuYmpq6DxEjXzNKCLEvl8v9SreOMKLaG4h4FBhjSjMpAkBhfn5eaa7pKHDBBRcgYyzyo3Av9PFMmJ+fHwKAgso2GWOHYWZm5rDqAw2FQmF7oNHpEmZmZr6PiJGtHSWEeDaTycRnoVcgn88r9YSUkszOzh6G7du324g4r7JxwzB2BBue7uC1r30t55x/QbcOv4hy3zZKIpFQamBEnH/d615nAyGEUEoPqWzcMIxzggxONzE7O/tdRIzcDK0Q4sAzzzxzn24dYcUwjPNUtrfkWSCEEB8eqJ0BxaXreM1rXsM555/TrUM1jLHPv+Utb+m5HWfrQKknljy7lJHjeZWNU0ovCCgoXcnU1NQPhBAHdOtQhRDimUwm09MZJ0+HD554npCOgYUQSrMFAkDBsqyzA4pN1/H6178eGWN/p1uHKqLUFz+wLOts1TPQS54FQgip1+vPqRadyWQuDCY83Ukmk3lACNH1NYWFEPsymUyczP4U+OGFJc8CIYSMjIwcRUTVe6L/KJjwdC+e531WtwYFfYhnnk+Dai8g4sLIyMhRQpZlpZRSPqtY9EXBhKd7yWazj3bzHmkhxIFsNvtz3TrCjmovLPfqcgMrfZAopedWKpX4aOFpEEJ07aH3OGXQ6alUKkOU0nNVtrncq8cNjIhPqLwIpZQUCoVLfY9Ql2Oa5o+7sRSLlJK3Wq17dOsIO4VC4VLV5YuWe/W4gev1+uOqxRuG8Se+RicCdEqxPKpbx3pBxN+USiWl8yZRxDCM/6K6zVqtdtyrxw08PDw8L4RQuk+XUnqFv+GJBlLKx3RrOAPNXT+DHgSqPSCEeGlkZOT41ueTS6vsVXkxANjSaDTiXVmngXOufBnPb6SUk7o1hJ1Go7ETAFQnuDjBoycYWAjxS9Wd6Ovru9KH2EQK1Weyg8DzvLJuDWEnnU6/U3WbJ3v0BAPbtv2w6gsCwLvVhyZaOI7TjXuIu27iLWgopX+qus2TPXqCgQcHBw8LIVSfTLrAsqw4S8cpyGazXVcQzjCMom4NYcayrK2GYSjd/yyEODQ4OHhCAo5XlBeVUipfmM/lcu9RH6LoYBhG1+0b70bNQeLHM7+SN19hYCHET1VfmFL6Z6rbjBKGYVyiW8N6AYCu0xwkfjzzK3nzFQY2TfNhKaXSekmGYeyMZ6NXZn5+PgsAV+nWsV4A4HLTNOOddivQaDTONwxD6fMupbSr1eor5qheYeCxsTEXER9S3al0Ov0R1W1GgVKpdAOltOu+Jymlqf7+/k/q1hFG0un0n6tuExEfGh8ff8XEIazyl+9VLQAAPjA7OxvXDl5Gs9k8K5FIdG12DsMwPt5qteI3q2XMzc2lAOADqttdzZMrGrher/9ESslVCgCAoVKpdLXqjnUr1Wo1m8lkfkgp7dpyrJTSVDqd/mGj0YgrUnYYHBy8GgCUplWWUvJ6vf6Tdf0jzvnPpGI45123ZdAPTNPMCiGUx1cXnPMnGo1G130G+AHn/DEf4vuz1a4Hq/0BIt6lunOGYVzUbDbP1x1kndTr9fF8Pv8IAFyuW4sqDMN4Uzab3d9sNnt6vb/ZbJ5vGIbyc/Cn8uKqBrYsy5djbul0+q9Vt9ktuK57ZS6X+61hGG/SrUU1ALAjm80+7bruNbq16CKdTv+N6jallK5lWWeWMJBzfrfq1wFEbFuWNa472EHSaDQmGGM/DOJ1Ngxwzn/abDZ7aqOHZVnjiNj2IZZ3n+q6cKo/ZIx9S3VHKaWpXC6n/JcqjFiWNcoY+1IulzuYSCR6ZjeaYRhXZLPZ5xljtzUajZ6oVpnL5f6GUqp8lWVDHpycnEwIIY6o/lVBxEa1Wo3szGWz2dzJOb8dEZ1gxrzwgoiMMfadVqsVuc+GJarVagkRG6pjJ4Q4Mjk5mdiQOMbYF/y4sZ7n3aw78CqpVCrFdrv9F5zzX/tvi+6Ec/7bdrv9MdM0I1W90vO8m/2IF2Ns4xk/a7XaBCIy1eIQsVatVrv6Rh47dqzQbrev4ZzfG4+2awcR25zzBz3P+1ClUunqN7FqtTqEiDUfYsRqtZqazw/O+f1+3EjGWNdVcrcs62zP8z7OOf+FH5MWvQYiMs75I57nfaJWq3VdVUvG2C1+xIVzfv9arr+mdHm2be/KZDL/qrrzUkq31Wr9QT6fD21GikqlMlIoFC41DONthJDLDcM4S7emKCOEOEwIeUgI8YtGo/Ho0NCQ0tK3Kmk0Gltyudz/pZSmVbftOM7bstnsaRNsrDnfpRBiEgCU5rclhBDO+XeTyeQHVbd7piwsLIzm8/mLAOCPAeBiSum5qtOCxqwNKSWRUk4h4l5EfKTZbO7btGnTrG5dSzDG7kwkEh9S3a4Q4rlEIrGmcqRrfjLb7faHU6mU8mUlQgjatv3mXC6npUKBZVnbM5nMRYZhvJUQcpFhGNt06IhZG53MqXuFEPsdx9lXLBandehotVpvyGazT5DTLMWeCZ7nfaSvr+8OpY3Ozs6mhBAzPr3v73/ssceUB+JkDh48mLJt+y2e593IOb9fCFHx8fMuJgCEEBXO+f2e591o2/ZbDh486PuJt8ceeww45/t96s/Mek7trevd0PO8TySTyT1+BKXdbn8knU7fobpdy7LGs9nsVQDwDkrppQDQdfmnYtYOItpSykcR8UHbtu8rFovKX7ld1/1QX1/fnX7oZ4ztTqVS/3Otf39dBi6Xy/2bNm36d9XHpQghREpZtizrP5VKpepG25qfn+8fHBx8r2EYfw4AF1FKfR/dY8KHlBIR8VdCiDtN0/zB6Ohoc6NtmqZZGhgY+B2lVHk2EkRcWFxcfM3IyMiGda6K53mf8ut1iDH2zY1oq9Vqo4yxWxDR9EtjTHeCiDXG2J5arbahffiMsW/6pdHzvE+tV8+6p1crlUp/qVTyZRQmZO3T58tZXFzsz+fzn04kEv+dUhq/IsesipTS5Zx/pdlsfqFUKq1rpHMcZ1c6nVa+nErIy6NvtVp9zfDw8Lo0ndH6iJ/fwoj4+1qtdt5ag+s4zq5UKvUtAOiJTfMxakDEo57nXZ/JZP5lLX9/cXGxv1gsTgLAWX7oYYx9MpVK/eN6/90ZfRsuLi7e6lc5EAA4K5/Pr+nHwfO8m9Lp9C9i88asFwDYkk6nH/Q87+ann376tD4oFAp7/DIvIh5dXFz8WqABaLfb1/r5zeK67qp1Zaanp8HzvG/5ef2Y3oFz/r25ublVT/24rvtOP6/fbrevPVMfnvHs7IsvvniHEOKAXz8QqVTqW/V6fXSlP9u6devXk8nkh/26dkxvYRjGNUNDQytOoNbr9fFUKrWhydVTIYQ48OKLL95xpv/+jA28Y7Gp4yUAAAg7SURBVMcOZIzt9qtjlNKhbDb7vUOHDp2g0XXdG5LJ5F/6dd2Y3iSRSHyo3W7fuPz/TU9PQzab/Y4fS0ZLMMZ279ix44yL2214ky9j7N5EIuFbZQHG2N+nUqnPEUJIq9U6O5PJ/Jsfm8djYqSU3HGcN+ZyuWcJeXmOJZlMft6v6wkh7kskEhuq3rlhAzcaja25XO55H02Fruu+I5PJ/Avn/GeGYUQmm2NM+BBCPJ5IJP7IcZwr0un0g8SHvc6EHD+Jd04+n39pI+1sWFw+n3+Jc+5ndg3o6+u7q9VqXRubN8ZvDMO40HGcj/f19X2P+GReQgjhnN+8UfMSomAEJoSQmZmZ1Ojo6CQA9FQmwpiYMwERD83Pz5+3efNmb6NtKTvo6jjOxel0+hHi469WTEwEQNd1/ziTyexV0Zgys2Uymb2c83/SF5eYmPDDOf8nVeYlRPFo2Wg0PomIh4MPS0xM+EHEw41GQ2lJVqUGLpVKdc/zriOEnPG6VkxMREHP864rlUp1lY0q/17NZDIPcc5vDS4uMTHhh3N+ayaTeUh1u75MOFmW9UlEnPI/LDEx4QcRp0zT/B9+tO1bukXbtnem0+kn4l1TMb2MlNJ1HOfNuVzOl3MDvi35ZLPZA37ulY6J6QYYY7v9Mi8hPo7AyzpwdyKReK/f14mJCRuc83uSyeR/9fMavm+6qNfr1yOilty9MTG6QMTper1+vd/XCaTkQKvV2pHJZJ6glPYHcb2YGJ1IKZud717fJ3ID2faYy+WmPM/7IInXh2OiD3qe98EgzEtIgPuW0+n0fYwx385WxsSEAcbY59Pp9H1BXS/wql2MsbsSicT7gr5uTIzfcM5/kEwm3x/kNQM/OWRZ1keEEL8K+roxMX4ihPiVZVkfCfq6Wupm1uv1oVwutz8+PxwTBRDxUKvVemuhUFgI+traCt82Go2zcrncfkrphkpdxMToREo522q13prP53+v4/paK1e3Wq2dmUzml5TSok4dMTFngpTSchznEj93Wp0O7aXnXde9MJVK/SJeI47pJqSUTc/z/iSdTj+uU4f29DfpdPpxx3HeJaV0dWuJiVkLnQMK79JtXkJCMAIv4TjO5X19fffHp5diwoyU0m232+/KZDI/162FkBAZmJDYxDHhJmzmJSQEr9DLyWQyP3cc5x1SSv8qlMfEnAGd/c3vCJN5CQnZCLxEZ2Lrp/HsdEwYkFJanue9PQzfvCcTqhF4ic7E1iVSylndWmJ6GynlrOM4l4TRvISE1MCEEJLL5Q60Wq23IuIh3VpiepOlHVY613lPR2gNTAgh+Xz+961W663x3umYoBFC/ErnDqu1EmoDE0JIoVBYqFarb+Oc/0C3lpjegHP+g2q1+jYde5vXSygnsVaj3W7f1KkVHPofnpiuBBljn0+lUn+vW8ha6SoDE0KI67pXpVKp78RbL2NU0tka+cEgD+OroOsMTMjLObbS6fSPAGC7bi0x3Q8iTruu+6dBpcFRSVe+iuZyuSnTNN/MOb9Ht5aY7oZzfo9pmoEkoPODrhyBl9Nut29IJpN74u2XMetBSukyxnb39fV1dR2vrjcwIS+fK06n03cBwA7dWmLCDyJOua77/jCv766VrnyFPplcLnegWq3+Ief8ayROXRuzOsg5/1q1Wv3DKJiXkIiMwMtxHOeyVCr1TQCY0K0lJjwg4mHP867zo8SnTiIxAi8nk8k8VKvVzuOcf4PEo3HMy6PuN2q12nlRMy8hERyBl+M4zsWpVOr2OPtlb4KIhzzPuz6TyezVrcUvIjcCLyeTyeydn58/jzH22ThlT+/QmWH+7Pz8/HlRNi8hER+Bl9NoNLam0+k9iUTiKt1aYvyDc36f67q78/n8S7q1BEHPGHgJx3EuSyaTewzD2KlbS4w6hBAHGGO7o/ideyoi/Qq9EplM5qFDhw693vO86xDxqG49MRsDEY96nnfdoUOHXt9r5iWkB0fg5czNzaU3bdp0g2EYnwaAId16YtYOIi4IIb64uLh469jYWM/Ob/S0gZeoVCr9AwMDHzMMY3ds5HDTMe6eWq32teHh4Z5PfhgbeBnlcrm/WCx+tGPkuGZTiEDEWSHEHsuyvjEyMtLzxl0iNvAKzM3NpUql0jWGYew2DONc3Xp6GSHEc0KIPdVq9ftjY2Oebj1hIzbwabBte1cqlforAHgnpTShW08vIKXkiPiA53lfzWazD+vWE2ZiA6+RWq02kc1mrweADwPAFt16oggiHkXEO2zbvn1gYOCwbj0xEWRychIcx7mCc343IjoyZkMgosM5v9txnCsmJyfjN5x1Eo/AG+DYsWOFwcHBqwHg/QCwK37FXhvy5VfkhxHxLsuyfjwyMlLXralbiQ2siIWFhVKhULgSAN4NAJdRSrO6NYUJKaWNiA8h4r31ev0nQ0NDVd2aokBsYB+YnZ1Nl0qlXYZhvJ1SerlhGD15GkoIcUhK+XMhxE+r1erD4+PjPbvhwi9iAweAaZoT2Wx2l2EYlxBCLjYMY6tuTX4ghHiJELJXCPFL27YfHhwcjCeifCY2sAYqlcpooVC4EADeTCl9A6X0/G7bAYaIC1LKZ6WUTyHiE/V6/fHh4eF53bp6jdjAIaFcLm8pFArnGoaxgxByDgBsk1KeDQCjlOq5TVJKgojzlNJDiPgCIeR5IcRUvV5/bmRkJD4IEgJiA4ec6enp7ObNmyeSyeQEAGyhlI4TQsYIIUMAUCSElKSURQDIImI/IaQfAOBk03fMiISQJgA0EdGmlFqEkCoiWoSQBULInJRyFhGPMsYOz8zMHN6+fbutOwYxq/P/AIOpi4VcZv9SAAAAAElFTkSuQmCC";

        // Posts
        public const int FIRST_PAGE = 1;
        public const int NUMBER_OF_POSTS = 5;

        #region 'Error Messages'
        public const string resNotFoundCountry       = "Država nije pronađena";
        public const string resNotFoundCity          = "Grad nije pronađen";
        public const string resNotFoundLocation      = "Lokacija nije pronađena";
        public const string resNotFoundMessage       = "Poruka nije pronađena"; 
        public const string resDeleteFailed         = "Neuspešno brisanje {0}";
        public const string resNullValue            = "Poslata je NULL vrednost za argument";
        public const string resNoFoundComment       = "Nije pronađen komentar";
        public const string resNoFoundUser          = "Nije pronađen korisnik";
        public const string resCommentDislikeExists = "Dislike već postoji";
        public const string resNoFoundPost          = "Post nije pronađen";
        public const string resDeleteLikeFailed     = "Neuspešno brisanje like-a";
        public const string resDeleteDislikeFailed  = "Neuspešno brisanje dislike-a";
        public const string resPermissionDenied     = "Odbijen pristup!"; 
        #endregion

        #region 'Ok message'
        public const string resDeletedDislike = "Dislike je uspešno obrisan!";
        public const string resDeletedLike    = "Like je uspešno obrisan!";
        #endregion

        public const int TAKE_ELEMENT = 5;
        public const double DISTANCE = 1500; 
        // Comment status
    }
    public enum CommentState
    {
        DISLIKED = -1,
        NONE = 0,
        LIKED = 1
    }
    public enum SearchType
    {
        LOCATION, 
        COORDINATES, 
        CITY, 
        COUNTRY
    }
}
