
export interface PrihodRequestDTO {
  opis: string;
  iznos: number;
  vrsta: string;
  korisnikId: number;
  prihodKategorija: PrihodEnum; // PrihodEnum
}


export interface PrihodResponseDTO {
  id: number;
  opis: string;
  iznos: number;
  vrsta: string;
  prihodKategorija: string; 
  datumTransakcije: string; 
  korisnikId: number;
}


export interface TrosakResponseDTO {
  id: number;
  opis: string;
  iznos: number;
  vrsta: string;
  trosakKategorija: string;
  datumTransakcije: string; 
  korisnikId: number;
}


export interface PodsjetnikResponseDTO {
  id: number;
  naziv: string;
  opis?: string;
  obavijesten: boolean;
  datumPodsjetnika: string; 
}


export interface PodsjetnikRequestDTO {
  stednjaId: number;
  naziv: string;
  opis?: string;
  datumPodsjetnika: string; 
}

export interface StednjaResponseDTO {
  id: number;
  naziv: string;
  opis?: string;
  datumKreiranja: string; // ISO date (YYYY-MM-DD)
  datumKraj: string;      // ISO date (YYYY-MM-DD)
  ciljniIznos: number;
  trenutniIznos: number;
  korisnikId: number;
  podsjetnici: PodsjetnikResponseDTO[];
}

export interface StednjaRequestDTO {
  naziv: string;
  opis?: string;
  datumKreiranja: string; 
  datumKraj: string;      
  ciljniIznos: number;
  trenutniIznos: number;
  korisnikId: number;
}


export interface TrosakRequestDTO {
  opis: string;
  iznos: number;
  vrsta: string;
  korisnikId: number;
  trosakKategorija: TrosakEnum;
}

export enum PrihodEnum {
  FREELANCE = "FREELANCE",
  PRODAJA = "PRODAJA",
  PLAĆA = "PLAĆA",
  POKLON = "POKLON",
  NAJAM = "NAJAM",
  OSTALO = "OSTALO",
}

export enum TrosakEnum {
  ODJEĆA = "ODJEĆA",
  REŽIJE = "REŽIJE",
  HRANA = "HRANA",
  ZDRAVLJE = "ZDRAVLJE",
  VOZILA = "VOZILA",
  OBRAZOVANJE = "OBRAZOVANJE",
  OSTALO = "OSTALO",
}


export type TransactionResponseDTO = PrihodResponseDTO | TrosakResponseDTO;
export type TransactionRequestDTO = PrihodRequestDTO | TrosakRequestDTO;

export interface ValutaResponseDTO {
  id: number;
  kod: string;
  simbol: string;
  naziv: string;
}
