import type { NextApiRequest, NextApiResponse } from 'next';
import { serialize } from 'cookie'

export default function handler(req: NextApiRequest, res: NextApiResponse) {
  if (req.method !== 'POST') {
    return res.status(405).json({ message: 'Method Not Allowed' });
  }

  const { email, password } = req.body;

  if (!email || !password) {
    return res.status(400).json({ message: 'Missing credentials' });
  }

  // Simulate success
  if (email === 'abc@abc.com' && password === 'abc') {

    const token = 'mock-session-token';
    res.setHeader('Set-Cookie', serialize('session', token, {
      path: '/',
      httpOnly: true,
      sameSite: 'lax',
      maxAge: 60 * 60 * 24 // 1 day
    }))

    return res.status(200).json({ message: 'Login successful', userId: 'abc' });
  }

  return res.status(401).json({ message: 'Invalid email or password' });
}