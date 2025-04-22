import type { NextApiRequest, NextApiResponse } from 'next';

export default function handler(req: NextApiRequest, res: NextApiResponse) {
  const { name, email, password } = req.body;

  if (!name || !email || !password) {
    return res.status(400).json({ message: 'All fields are required' });
  }

  // TODO: Add DB logic here. For now, simulate success.
  console.log('New user registered:', { name, email });

  return res.status(200).json({ message: 'User registered successfully' });
}