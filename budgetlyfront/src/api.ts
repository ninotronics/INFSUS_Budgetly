// src/api.ts
import axios from 'axios';
import {
  PrihodRequestDTO,
  PrihodResponseDTO,
  StednjaRequestDTO,
  StednjaResponseDTO,
  TrosakRequestDTO,
  TrosakResponseDTO,
  TransactionResponseDTO,
  PodsjetnikRequestDTO,
  PodsjetnikResponseDTO,
  ValutaResponseDTO
} from './types1';

const api = axios.create({ baseURL: '/api' });


export const fetchIncomes = () =>
  api.get<PrihodResponseDTO[]>('/prihodi').then(r => r.data);
export const fetchIncome = (id: number) =>
  api.get<PrihodResponseDTO>(`/prihodi/${id}`).then(r => r.data);
export const createIncome = (p: PrihodRequestDTO) =>
  api.post<PrihodResponseDTO>('/prihodi', p).then(r => r.data);
export const updateIncome = (id: number, p: PrihodRequestDTO) =>
  api.put<PrihodResponseDTO>(`/prihodi/${id}`, p).then(r => r.data);
export const deleteIncome = (id: number) =>
  api.delete<void>(`/prihodi/${id}`).then(r => r.data);

export const fetchSavings = () =>
  api.get<StednjaResponseDTO[]>('/stednja').then(r => r.data);
export const fetchSaving = (id: number) =>
  api.get<StednjaResponseDTO>(`/stednja/${id}`).then(r => r.data);
export const createSaving = (s: StednjaRequestDTO) =>
  api.post<StednjaResponseDTO>('/stednja', s).then(r => r.data);
export const updateSaving = (id: number, s: StednjaRequestDTO) =>
  api.put<StednjaResponseDTO>(`/stednja/${id}`, s).then(r => r.data);
export const deleteSaving = (id: number) =>
  api.delete<void>(`/stednja/${id}`).then(r => r.data);

// ─── Reminders (Podsjetnici) ───────────────────────────────────────
export const fetchReminders = () =>
  api.get<PodsjetnikResponseDTO[]>('/podsjetnici').then(r => r.data);
export const fetchReminder = (id: number) =>
  api.get<PodsjetnikResponseDTO>(`/podsjetnici/${id}`).then(r => r.data);
export const createReminder = (p: PodsjetnikRequestDTO) =>
  api.post<PodsjetnikResponseDTO>('/podsjetnici', p).then(r => r.data);
export const deleteReminder = (id: number) =>
  api.delete<void>(`/podsjetnici/${id}`).then(r => r.data);
export const updateReminder = (id: number, p: PodsjetnikRequestDTO) =>
  api.put<PodsjetnikResponseDTO>(`/podsjetnici/${id}`, p).then(r => r.data);


export const fetchTransactions = () =>
  api.get<TransactionResponseDTO[]>('/transakcije').then(r => r.data);

export const fetchExpenses = () =>
  api.get<TrosakResponseDTO[]>('/troskovi').then(r => r.data);
export const fetchExpense = (id: number) =>
  api.get<TrosakResponseDTO>(`/troskovi/${id}`).then(r => r.data);
export const createExpense = (t: TrosakRequestDTO) =>
  api.post<TrosakResponseDTO>('/troskovi', t).then(r => r.data);
export const updateExpense = (id: number, t: TrosakRequestDTO) =>
  api.put<TrosakResponseDTO>(`/troskovi/${id}`, t).then(r => r.data);
export const deleteExpense = (id: number) =>
  api.delete<void>(`/troskovi/${id}`).then(r => r.data);


export const fetchValutas = () =>
  api.get<ValutaResponseDTO[]>('/valute').then(r => r.data);

export const fetchValuta = (id: number) =>
  api.get<ValutaResponseDTO>(`/valute/${id}`).then(r => r.data);